package com.example.BarrierKU.domain.path.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.door.repository.DoorRepository;
import com.example.BarrierKU.domain.facility.repository.FacilityRepository;
import com.example.BarrierKU.domain.path.dto.GeoJsonFeatureCollection;
import com.example.BarrierKU.domain.path.dto.GraphNode;
import com.example.BarrierKU.domain.path.factory.GeoJsonFactory;
import com.example.BarrierKU.domain.path.model.Node;
import com.example.BarrierKU.domain.path.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.BarrierKU.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PathService {

    private final NodeRepository nodeRepository;
    private final JdbcTemplate jdbcTemplate;
    private final BuildingRepository buildingRepository;
    private final FacilityRepository facilityRepository;
    private final DoorRepository doorRepository;

    private static final String BUILDING = "BUILDING";
    private static final String FACILITY = "FACILITY";

    /**
     * 최단 경로 추천
     *
     * @param srcId - 출발지 Id
     * @param srcType - 출발지 Type (BUILDING / FACILITY)
     * @param destId - 도착지 Id
     * @param destType - 도착지 Type (BUILDING / FACILITY)
     * @return
     */
    public GeoJsonFeatureCollection findShortestPath(Long srcId, String srcType, Long destId, String destType) {
        // 1. 출발/도착 BuildingId 추출
        Long srcBuildingId = getBuildingId(srcId, srcType);
        Long destBuildingId = getBuildingId(destId, destType);

        // 2. 출발/도착 건물이 동일하면 예외
        if (srcBuildingId.equals(destBuildingId)) {
            throw new BarrierKuException(SAME_SOURCE_AND_DESTINATION);
        }

        // 3. 출발/도착 위치 계산
        Point srcPoint = getSpotByBuildingId(srcBuildingId);
        double startLat = srcPoint.getY();
        double startLon = srcPoint.getX();

        Point destPoint = getNearestDoorSpotByBuildingId(destBuildingId, startLat, startLon);
        double endLat = destPoint.getY();
        double endLon = destPoint.getX();

        // 4. 출발/도착 노드 조회
        Node startNode = findNearestNode(startLat, startLon);
        Node endNode = findNearestNode(endLat, endLon);

        int source = parseNodeId(startNode.getUid());
        int dest = parseNodeId(endNode.getUid());

        // 5. pgr_dijkstra 경로 탐색
        String sql = """
            SELECT r.seq, r.node, r.edge, r.cost, n.uid, ST_Y(n.location) AS lat, ST_X(n.location) AS lng,
                   e.length AS distance
            FROM (
                SELECT * FROM pgr_dijkstra(
                    $$
                    SELECT id,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER) AS source,
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER) AS target,
                           length AS cost,
                           length AS reverse_cost
                    FROM way
                    UNION ALL
                    SELECT id + 100000,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER),
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER),
                           length, length
                    FROM crossing
                    UNION ALL
                    SELECT id + 200000,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER),
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER),
                           length, length
                    FROM street
                    $$,
                    ?, ?, false
                )
            ) r
            JOIN node n ON CAST(SPLIT_PART(n.uid, '#', 2) AS INTEGER) = r.node
            LEFT JOIN (
                SELECT id, length FROM way
                UNION ALL
                SELECT id + 100000 AS id, length FROM crossing
                UNION ALL
                SELECT id + 200000 AS id, length FROM street
            ) e ON r.edge = e.id
            ORDER BY r.seq;
        """;

        List<GraphNode> nodes = new ArrayList<>();
        List<Double> distances = new ArrayList<>();

        jdbcTemplate.query(sql, new Object[]{source, dest}, rs -> {
            nodes.add(new GraphNode(
                    rs.getString("uid"),
                    rs.getDouble("lat"),
                    rs.getDouble("lng")
            ));
            distances.add(rs.getDouble("distance"));
        });

        double startPointToStartNodeDistance = getDistanceFromPointToNode(startLon, startLat, startNode.getUid());
        double endNodeToEndPointDistance = getDistanceFromPointToNode(endLon, endLat, endNode.getUid());

        return GeoJsonFactory.from(
                nodes, distances,
                startLat, startLon, endLat, endLon,
                startPointToStartNodeDistance, endNodeToEndPointDistance
        );
    }

    /**
     * 출발지/도착지가 BUILDING(건물) 인 경우 - 건물 ID
     * 출발지/도착지가 FACILITY(편의시설) 인 경우 - 해당 편의시설이 속해있는 건물의 ID
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
     * 출발지 -> 출발 건물의 위도/경도를 그대로 활용
     */
    private Point getSpotByBuildingId(Long buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND))
                .getSpot();
    }

    /**
     * 도착지 -> 도착 건물에 존재하는 문들 중 출발지와 가장 가까운 문을 도착지로 활용
     */
    private Point getNearestDoorSpotByBuildingId(Long buildingId, double fromLat, double fromLon) {
        return doorRepository.findNearestDoorSpot(buildingId, fromLat, fromLon)
                .orElseThrow(() -> new BarrierKuException(DOOR_NOT_FOUND));
    }

    private Node findNearestNode(double lat, double lon) {
        return nodeRepository.findNearestNode(lat, lon)
                .orElseThrow(() -> new BarrierKuException(NODE_NOT_FOUND));
    }

    /**
     * 현재 좌표로부터 특정 노드까지의 거리 계산
     */
    private double getDistanceFromPointToNode(double lon, double lat, String nodeUid) {
        return jdbcTemplate.queryForObject("""
            SELECT ST_DistanceSphere(
                ST_SetSRID(ST_MakePoint(?, ?), 4326),
                location
            ) FROM node WHERE uid = ?
        """, Double.class, lon, lat, nodeUid);
    }

    private int parseNodeId(String uid) {
        return Integer.parseInt(uid.split("#")[1]);
    }
}

