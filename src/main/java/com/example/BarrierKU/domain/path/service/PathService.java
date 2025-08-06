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
import java.util.List;

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

    /**
     * 하나의 메서드에서 두 가지 경로 (최단, 배리어프리)를 계산하고 응답 DTO 로 감싸 반환
     */
    public PathRecommendationsResponse findAllPaths(Long srcId, String srcType, Long destId, String destType) {
        Long srcBuildingId = getBuildingId(srcId, srcType);
        Long destBuildingId = getBuildingId(destId, destType);

        if (srcBuildingId.equals(destBuildingId)) {
            throw new BarrierKuException(SAME_SOURCE_AND_DESTINATION);
        }

        Point srcPoint = getSpotByBuildingId(srcBuildingId);
        double startLat = srcPoint.getY();
        double startLon = srcPoint.getX();

        return new PathRecommendationsResponse(
                findPath(startLat, startLon, destBuildingId, SHORTEST),
                findPath(startLat, startLon, destBuildingId, NO_STAIRS),
                findPath(startLat, startLon, destBuildingId, BARRIER_FREE)
        );
    }

    /**
     * 실제 경로를 탐색하는 로직 (경로 유형에 따라 도착지 결정 및 SQL 분기)
     */
    private GeoJsonFeatureCollection findPath(double startLat, double startLon, Long destBuildingId, PathType pathType) {
        Point destPoint = getBestDoorSpot(destBuildingId, startLat, startLon, pathType);
        double endLat = destPoint.getY();
        double endLon = destPoint.getX();

        Node startNode = findNearestNode(startLat, startLon);
        Node endNode = findNearestNode(endLat, endLon);

        int source = parseNodeId(startNode.getUid());
        int dest = parseNodeId(endNode.getUid());

        String sql = PathSqlFactory.getSql(pathType);

        List<GraphEdge> edges = new ArrayList<>();

        final String[] previousNodeUid = {null};
        final double[] previousLat = {0};
        final double[] previousLng = {0};

        jdbcTemplate.query(sql, new Object[]{source, dest}, rs -> {
            String currentUid = rs.getString("uid");
            double lat = rs.getDouble("lat");
            double lng = rs.getDouble("lng");

            if (previousNodeUid[0] != null) {
                edges.add(new GraphEdge(
                        previousNodeUid[0],  // from
                        currentUid,          // to
                        previousLat[0],
                        previousLng[0],
                        lat,
                        lng,
                        rs.getDouble("cost"),     // weight
                        rs.getDouble("distance")  // distance
                ));
            }

            previousNodeUid[0] = currentUid;
            previousLat[0] = lat;
            previousLng[0] = lng;
        });

        if (edges.isEmpty()) {
            return GeoJsonFactory.empty();
        }

        double startPointToStartNodeDistance = getDistanceFromPointToNode(startLon, startLat, startNode.getUid());
        double endNodeToEndPointDistance = getDistanceFromPointToNode(endLon, endLat, endNode.getUid());

        return GeoJsonFactory.fromEdges(
                edges,
                startLat, startLon,
                endLat, endLon,
                startPointToStartNodeDistance,
                endNodeToEndPointDistance
        );
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