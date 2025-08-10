package com.example.BarrierKU.domain.path.factory;

import com.example.BarrierKU.domain.path.dto.GeoJsonFeature;
import com.example.BarrierKU.domain.path.dto.GeoJsonFeatureCollection;
import com.example.BarrierKU.domain.path.dto.GraphEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoJsonFactory {

    public static GeoJsonFeatureCollection empty() {
        return new GeoJsonFeatureCollection(List.of(), "0m");
    }

    public static GeoJsonFeatureCollection fromEdges(
            List<GraphEdge> edges,
            double startLat, double startLon,
            double endLat, double endLon,
            double startPointToStartNodeDistance,
            double endNodeToEndPointDistance
    ) {
        List<GeoJsonFeature> features = new ArrayList<>();
        double totalDistance = startPointToStartNodeDistance + endNodeToEndPointDistance;

        int pointIndex = 1;
        int lineIndex = 1;

        // 1. 시작 Point
        features.add(new GeoJsonFeature(
                "Feature",
                new GeoJsonFeature.Geometry("Point", List.of(startLon, startLat)),
                Map.of("type", "point", "index", pointIndex++)
        ));

        // 2. 시작 → 첫 from Point Line
        if (!edges.isEmpty()) {
            GraphEdge first = edges.get(0);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("LineString",
                            List.of(
                                    List.of(startLon, startLat),
                                    List.of(first.fromLng(), first.fromLat())
                            )
                    ),
                    Map.of("type", "line", "index", lineIndex++)
            ));

            // 첫 from 노드 Point 추가
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("Point",
                            List.of(first.fromLng(), first.fromLat())
                    ),
                    Map.of("type", "point", "index", pointIndex++)
            ));
        }

        // 3. 각 GraphEdge 를 순회하며 from → to 처리
        for (GraphEdge edge : edges) {
            // Line from → to
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("LineString",
                            List.of(
                                    List.of(edge.fromLng(), edge.fromLat()),
                                    List.of(edge.toLng(), edge.toLat())
                            )
                    ),
                    Map.of("type", "line", "index", lineIndex++)
            ));
            totalDistance += edge.distance();

            // to Point
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("Point",
                            List.of(edge.toLng(), edge.toLat())
                    ),
                    Map.of("type", "point", "index", pointIndex++)
            ));
        }

        // 4. 마지막 to → 도착지 Line
        if (!edges.isEmpty()) {
            GraphEdge last = edges.get(edges.size() - 1);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("LineString",
                            List.of(
                                    List.of(last.toLng(), last.toLat()),
                                    List.of(endLon, endLat)
                            )
                    ),
                    Map.of("type", "line", "index", lineIndex++)
            ));
        }

        // 5. 도착지 Point
        features.add(new GeoJsonFeature(
                "Feature",
                new GeoJsonFeature.Geometry("Point", List.of(endLon, endLat)),
                Map.of("type", "point", "index", pointIndex)
        ));

        return new GeoJsonFeatureCollection(features, String.format("%.0fm", totalDistance));
    }

}