package com.example.BarrierKU.domain.path.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.door.repository.DoorRepository;
import com.example.BarrierKU.domain.facility.repository.FacilityRepository;
import com.example.BarrierKU.domain.path.dto.GeoJsonFeatureCollection;
import com.example.BarrierKU.domain.path.dto.GraphEdge;
import com.example.BarrierKU.domain.path.dto.PathRecommendationsResponse;
import com.example.BarrierKU.domain.path.factory.GeoJsonFactory;
import com.example.BarrierKU.domain.path.factory.PathSqlFactory;
import com.example.BarrierKU.domain.path.model.Node;
import com.example.BarrierKU.domain.path.model.PathType;
import com.example.BarrierKU.domain.path.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.BarrierKU.common.response.ResponseCode.*;
import static com.example.BarrierKU.domain.path.model.PathType.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PathService {

    private final NodeRepository nodeRepository;
    private final JdbcTemplate jdbcTemplate;
    private final BuildingRepository buildingRepository;
    private final FacilityRepository facilityRepository;
    private final DoorRepository doorRepository;

    private static final String BUILDING = "BUILDING";
    private static final String FACILITY = "FACILITY";

    private record PathResult(GeoJsonFeatureCollection collection, double totalCost) {}

    /**
     * 출발 후보 정보: (문 좌표 lat, lon) + 해당 문과 가장 가까운 그래프 노드
     * 여러 문이 같은 노드에 매핑될 수 있으므로, 노드 기준 중복 제거에 사용
     */
    private record StartCandidate(double lat, double lon, Node node) {}

    /**
     * 하나의 메서드에서 세 가지 경로 (최단, 계단 제외, 배리어프리)를 계산하고 응답 DTO 로 감싸 반환
     */
    public PathRecommendationsResponse findAllPaths(Long srcId, String srcType, Long destId, String destType) {
        Long srcBuildingId = getBuildingId(srcId, srcType);
        Long destBuildingId = getBuildingId(destId, destType);

        if (srcBuildingId.equals(destBuildingId)) {
            throw new BarrierKuException(SAME_SOURCE_AND_DESTINATION);
        }

        GeoJsonFeatureCollection shortestPath = findPathWithDoorCandidates(srcBuildingId, destBuildingId, SHORTEST);
        GeoJsonFeatureCollection noStairsPath = findPathWithDoorCandidates(srcBuildingId, destBuildingId, NO_STAIRS);
        GeoJsonFeatureCollection barrierFreePath = findPathWithDoorCandidates(srcBuildingId, destBuildingId, BARRIER_FREE);

        return PathRecommendationsResponse.of(shortestPath, noStairsPath, barrierFreePath);
    }

    /**
     * 모든 경로 유형에 대해 출발지를 문 기반으로 통일하여 처리하는 로직
     *
     * 1. 출발 건물의 문들을 조회
     * 2. 각 문에 대해 가장 가까운 그래프 노드를 찾고
     * 3. 노드 기준으로 중복 제거
     * 4. 각 시작 후보에 대해 경로 계산
     * 5. 총 비용이 가장 작은 경로를 반환
     */
    private GeoJsonFeatureCollection findPathWithDoorCandidates(Long srcBuildingId, Long destBuildingId, PathType pathType) {
        Point destCenter = getSpotByBuildingId(destBuildingId);
        double destLat = destCenter.getY();
        double destLon = destCenter.getX();

        List<Point> doorSpots = resolveStartDoorSpots(srcBuildingId, destLat, destLon, pathType);
        if (doorSpots.isEmpty()) {
            throw new BarrierKuException(DOOR_NOT_FOUND);
        }

        List<StartCandidate> candidates = resolveDistinctStartCandidates(doorSpots);

        return candidates.stream()
                .map(c -> findPath(c.lat(), c.lon(), c.node(), destBuildingId, pathType))
                .min(Comparator.comparingDouble(PathResult::totalCost))
                .map(PathResult::collection)
                .orElseGet(GeoJsonFactory::empty);
    }

    /**
     * 경로 유형에 따라 출발 문 후보를 조회
     *
     * - SHORTEST: 모든 문 사용
     * - NO_STAIRS / BARRIER_FREE:
     *      휠체어 출입 가능 문 우선 사용
     *      없을 경우 일반 문으로
     */
    private List<Point> resolveStartDoorSpots(Long srcBuildingId, double destLat, double destLon, PathType pathType) {
        if (pathType == SHORTEST) {
            return doorRepository.findDoorSpotsByBuildingIdOrderByDistanceTo(srcBuildingId, destLat, destLon);
        }
        List<Point> wheelchair = doorRepository.findWheelchairDoorSpotsByBuildingIdOrderByDistanceTo(
                srcBuildingId, destLat, destLon);
        if (!wheelchair.isEmpty()) {
            return wheelchair;
        }
        return doorRepository.findDoorSpotsByBuildingIdOrderByDistanceTo(srcBuildingId, destLat, destLon);
    }

    /**
     * 문 좌표 리스트를 시작 후보로 변환
     *
     * 여러 문이 동일한 그래프 노드에 매핑될 수 있으므로,
     * 노드 UID 기준으로 중복 제거하여 하나만 유지
     */
    private List<StartCandidate> resolveDistinctStartCandidates(List<Point> doorSpots) {
        Map<String, StartCandidate> byNodeUid = new LinkedHashMap<>();
        for (Point spot : doorSpots) {
            double lat = spot.getY();
            double lon = spot.getX();
            Node node = findNearestNode(lat, lon);
            byNodeUid.putIfAbsent(node.getUid(), new StartCandidate(lat, lon, node));
        }
        return new ArrayList<>(byNodeUid.values());
    }

    /**
     * 실제 경로를 탐색하는 로직 (경로 유형에 따라 도착지 결정 및 SQL 분기)
     */
    private PathResult findPath(double startLat, double startLon, Node startNode, Long destBuildingId, PathType pathType) {
        Point destPoint = getBestDoorSpot(destBuildingId, startLat, startLon, pathType);
        double endLat = destPoint.getY();
        double endLon = destPoint.getX();

        Node endNode = findNearestNode(endLat, endLon);

        int source = parseNodeId(startNode.getUid());
        int dest   = parseNodeId(endNode.getUid());

        String sql = PathSqlFactory.getSql(pathType);
        List<GraphEdge> edges = new ArrayList<>();

        final String[] previousNodeUid  = { null };
        final double[] previousLat      = { 0 };
        final double[] previousLng      = { 0 };
        final double[] previousWeight   = { 0 };
        final double[] previousDistance = { 0 };

        jdbcTemplate.query(sql, new Object[]{source, dest}, rs -> {
            String currentUid = rs.getString("uid");
            double lat      = rs.getDouble("lat");
            double lng      = rs.getDouble("lng");
            double cost     = rs.getDouble("cost");
            double distance = rs.getDouble("distance");

            if (previousNodeUid[0] != null) {
                edges.add(new GraphEdge(
                        previousNodeUid[0], currentUid,
                        previousLat[0], previousLng[0],
                        lat, lng,
                        previousWeight[0], previousDistance[0]
                ));
            }

            previousNodeUid[0]  = currentUid;
            previousLat[0]      = lat;
            previousLng[0]      = lng;
            previousWeight[0]   = cost;
            previousDistance[0] = distance;
        });

        if (edges.isEmpty()) {
            return new PathResult(GeoJsonFactory.empty(), Double.MAX_VALUE);
        }

        double startPointToStartNodeDistance = getDistanceFromPointToNode(startLon, startLat, startNode.getUid());
        double endNodeToEndPointDistance     = getDistanceFromPointToNode(endLon, endLat, endNode.getUid());
        double totalCost = startPointToStartNodeDistance + endNodeToEndPointDistance
                + edges.stream().mapToDouble(GraphEdge::weight).sum();

        GeoJsonFeatureCollection collection = GeoJsonFactory.fromEdges(
                edges, startLat, startLon, endLat, endLon,
                startPointToStartNodeDistance, endNodeToEndPointDistance
        );
        return new PathResult(collection, totalCost);
    }

    /**
     * 경로 유형(PathType)에 따라 휠체어 가능 문을 우선 고려하되, 없을 경우 일반 문 중에서 선택
     */
    private Point getBestDoorSpot(Long buildingId, double fromLat, double fromLon, PathType pathType) {
        if (pathType == NO_STAIRS || pathType == BARRIER_FREE) {
            return doorRepository.findNearestWheelchairDoorSpot(buildingId, fromLat, fromLon)
                    .orElseGet(() -> doorRepository.findNearestDoorSpot(buildingId, fromLat, fromLon)
                            .orElseThrow(() -> new BarrierKuException(DOOR_NOT_FOUND)));
        }

        return doorRepository.findNearestDoorSpot(buildingId, fromLat, fromLon)
                .orElseThrow(() -> new BarrierKuException(DOOR_NOT_FOUND));
    }

    /**
     * 출발지/도착지가 BUILDING(건물)인 경우는 그대로, FACILITY 인 경우 해당 건물 ID 추출
     */
    private Long getBuildingId(Long id, String type) {
        return switch (type) {
            case BUILDING -> buildingRepository.findById(id)
                    .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND))
                    .getId();
            case FACILITY -> facilityRepository.findById(id)
                    .orElseThrow(() -> new BarrierKuException(FACILITY_NOT_FOUND))
                    .getBuilding().getId();
            default -> throw new BarrierKuException(ILLEGAL_POINT_TYPE);
        };
    }

    /**
     * 출발 위치로 사용할 건물의 중심 좌표 반환
     */
    private Point getSpotByBuildingId(Long buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND))
                .getSpot();
    }

    /**
     * 가장 가까운 노드를 PostGIS 의 ST_DistanceSphere 로 계산하여 조회
     */
    private Node findNearestNode(double lat, double lon) {
        return nodeRepository.findNearestNode(lat, lon)
                .orElseThrow(() -> new BarrierKuException(NODE_NOT_FOUND));
    }

    /**
     * 특정 좌표와 노드 간 거리 계산 (계단, 배리어프리 구간 포함 고려)
     */
    private double getDistanceFromPointToNode(double lon, double lat, String nodeUid) {
        return jdbcTemplate.queryForObject("""
                    SELECT ST_DistanceSphere(
                        ST_SetSRID(ST_MakePoint(?, ?), 4326),
                        location
                    ) FROM node WHERE uid = ?
                """, Double.class, lon, lat, nodeUid);
    }

    /**
     * UID 형태 예: "11215#788" → 뒤의 숫자만 파싱
     */
    private int parseNodeId(String uid) {
        return Integer.parseInt(uid.split("#")[1]);
    }
}
