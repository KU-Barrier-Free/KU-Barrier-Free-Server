package com.example.BarrierKU.domain.path.dto;

import java.util.List;

public record GeoJsonFeatureCollection(
        String type,
        List<GeoJsonFeature> features,
        String totalDistance
) {
    public GeoJsonFeatureCollection(List<GeoJsonFeature> features, String totalDistance) {
        this("FeatureCollection", features, totalDistance);
    }
}
