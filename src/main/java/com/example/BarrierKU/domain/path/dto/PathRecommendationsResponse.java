package com.example.BarrierKU.domain.path.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "경로 추천 응답")
public record PathRecommendationsResponse(
        @Schema(description = "최단 경로", implementation = GeoJsonFeatureCollection.class)
        GeoJsonFeatureCollection shortestPath,

        @Schema(description = "계단 없는 경로", implementation = GeoJsonFeatureCollection.class)
        GeoJsonFeatureCollection noStairsPath,

        @Schema(description = "배리어프리 경로", implementation = GeoJsonFeatureCollection.class)
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


