package com.example.BarrierKU.domain.path.dto;

public record PathRecommendationsResponse(
        GeoJsonFeatureCollection shortestPath,
        GeoJsonFeatureCollection noStairsPath,
        GeoJsonFeatureCollection barrierFreePath
) {
    public static PathRecommendationsResponse of(
            GeoJsonFeatureCollection shortest,
            GeoJsonFeatureCollection noStairs,
            GeoJsonFeatureCollection barrierFree
    ) {
        return new PathRecommendationsResponse(shortest, noStairs, barrierFree);
    }
}

