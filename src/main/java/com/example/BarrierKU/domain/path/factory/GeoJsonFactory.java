package com.example.BarrierKU.domain.path.factory;

import com.example.BarrierKU.domain.path.dto.GeoJsonFeature;
import com.example.BarrierKU.domain.path.dto.GeoJsonFeatureCollection;
import com.example.BarrierKU.domain.path.dto.GraphNode;
import com.example.BarrierKU.domain.path.dto.GraphEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoJsonFactory {

    public static GeoJsonFeatureCollection empty() {
        return new GeoJsonFeatureCollection(List.of(), "0m");
    }

    public static GeoJsonFeatureCollection from(
            List<GraphNode> nodes,
            List<GraphEdge> edges,
            double startLat, double startLon,
            double endLat, double endLon,
            double startPointToStartNodeDistance, double endNodeToEndPointDistance
    ) {
        List<GeoJsonFeature> features = new ArrayList<>();

        // 출발 지점 추가
        features.add(new GeoJsonFeature(
                "Feature",
                new GeoJsonFeature.Geometry("Point", List.of(startLon, startLat)),
                Map.of("type", "point", "index", 1)
        ));

        double totalDistance = startPointToStartNodeDistance + endNodeToEndPointDistance;

        // 노드들을 포인트로 추가
        for (int i = 0; i < nodes.size(); i++) {
            GraphNode node = nodes.get(i);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("Point", List.of(node.lng(), node.lat())),
                    Map.of("type", "point", "index", i + 2)
            ));
        }

        // 출발점에서 첫 번째 노드까지의 라인
        if (!nodes.isEmpty()) {
            GraphNode firstNode = nodes.get(0);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("LineString",
                            List.of(
                                    List.of(startLon, startLat),
                                    List.of(firstNode.lng(), firstNode.lat())
                            )
                    ),
                    Map.of("type", "line", "index", 1)
            ));
        }

        // GraphEdge를 사용하여 노드 간 연결선 생성
        int lineIndex = 2; // 첫 번째 라인은 index 1이므로 2부터 시작
        for (GraphEdge edge : edges) {
            GraphNode fromNode = findNodeByUid(nodes, edge.from());
            GraphNode toNode = findNodeByUid(nodes, edge.to());

            if (fromNode != null && toNode != null) {
                totalDistance += edge.distance();

                features.add(new GeoJsonFeature(
                        "Feature",
                        new GeoJsonFeature.Geometry("LineString",
                                List.of(
                                        List.of(fromNode.lng(), fromNode.lat()),
                                        List.of(toNode.lng(), toNode.lat())
                                )
                        ),
                        Map.of("type", "line", "index", lineIndex++)
                ));
            }
        }

        // 마지막 노드에서 도착점까지의 라인
        if (!nodes.isEmpty()) {
            GraphNode lastNode = nodes.get(nodes.size() - 1);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("LineString",
                            List.of(
                                    List.of(lastNode.lng(), lastNode.lat()),
                                    List.of(endLon, endLat)
                            )
                    ),
                    Map.of("type", "line", "index", lineIndex)
            ));

            // 도착 지점 추가
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("Point", List.of(endLon, endLat)),
                    Map.of("type", "point", "index", features.size() + 1)
            ));
        }

        return new GeoJsonFeatureCollection(features, String.format("%.0fm", totalDistance));
    }

    /**
     * UID로 노드를 찾는 헬퍼 메서드
     */
    private static GraphNode findNodeByUid(List<GraphNode> nodes, String uid) {
        return nodes.stream()
                .filter(node -> node.uid().equals(uid))
                .findFirst()
                .orElse(null);
    }
}