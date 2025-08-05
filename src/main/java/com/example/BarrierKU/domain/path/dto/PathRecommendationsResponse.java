package com.example.BarrierKU.domain.path.dto;

public record PathRecommendationsResponse(
        GeoJsonFeatureCollection shortestPath,
        GeoJsonFeatureCollection barrierFreePath,
        GeoJsonFeatureCollection noStairsPath
) {
    public static PathRecommendationsResponse of(
            GeoJsonFeatureCollection shortest,
            GeoJsonFeatureCollection barrierFree,
            GeoJsonFeatureCollection noStairs
    ) {
        return new PathRecommendationsResponse(shortest, barrierFree, noStairs);
    }
}

