package com.example.BarrierKU.domain.path.dto;

public record PathRecommendationsResponse(
        GeoJsonFeatureCollection shortestPath,
        GeoJsonFeatureCollection barrierFreePath
) {
    public static PathRecommendationsResponse of(
            GeoJsonFeatureCollection shortest,
            GeoJsonFeatureCollection barrierFree
    ) {
        return new PathRecommendationsResponse(shortest, barrierFree);
    }
}

