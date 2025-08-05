package com.example.BarrierKU.domain.path.factory;

import com.example.BarrierKU.domain.path.dto.GeoJsonFeature;
import com.example.BarrierKU.domain.path.dto.GeoJsonFeatureCollection;
import com.example.BarrierKU.domain.path.dto.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoJsonFactory {

    public static GeoJsonFeatureCollection from(
            List<GraphNode> nodes,
            List<Double> distances,
            double startLat, double startLon,
            double endLat, double endLon,
            double startPointToStartNodeDistance, double endNodeToEndPointDistance
    ) {
        List<GeoJsonFeature> features = new ArrayList<>();

        features.add(new GeoJsonFeature(
                "Feature",
                new GeoJsonFeature.Geometry("Point", List.of(startLon, startLat)),
                Map.of("index", 1, "type", "point")
        ));

        double totalDistance = startPointToStartNodeDistance + endNodeToEndPointDistance;

        for (int i = 0; i < nodes.size(); i++) {
            GraphNode curr = nodes.get(i);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("Point", List.of(curr.lng(), curr.lat())),
                    Map.of("index", i + 2, "type", "point")
            ));

            if (i == 0) {
                features.add(new GeoJsonFeature(
                        "Feature",
                        new GeoJsonFeature.Geometry("LineString",
                                List.of(
                                        List.of(startLon, startLat),
                                        List.of(curr.lng(), curr.lat())
                                )
                        ),
                        Map.of("index", 1, "type", "line")
                ));
            } else {
                GraphNode prev = nodes.get(i - 1);
                totalDistance += distances.get(i);

                features.add(new GeoJsonFeature(
                        "Feature",
                        new GeoJsonFeature.Geometry("LineString",
                                List.of(
                                        List.of(prev.lng(), prev.lat()),
                                        List.of(curr.lng(), curr.lat())
                                )
                        ),
                        Map.of("index", i + 1, "type", "line")
                ));
            }
        }

        if (!nodes.isEmpty()) {
            GraphNode last = nodes.get(nodes.size() - 1);
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("LineString",
                            List.of(
                                    List.of(last.lng(), last.lat()),
                                    List.of(endLon, endLat)
                            )
                    ),
                    Map.of("index", features.size() + 1, "type", "line")
            ));
            features.add(new GeoJsonFeature(
                    "Feature",
                    new GeoJsonFeature.Geometry("Point", List.of(endLon, endLat)),
                    Map.of("index", features.size() + 1, "type", "point")
            ));
        }

        return new GeoJsonFeatureCollection(features, String.format("%.0fm", totalDistance));
    }
}
