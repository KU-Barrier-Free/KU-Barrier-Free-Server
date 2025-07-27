package com.example.BarrierKU.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceSearchResponse<T>(
    @Schema(description = "응답 타입", example = "BUILDING 또는 FACILITY") String type,
    @Schema(description = "응답 데이터", example = "{}") T data) {
    // data : 실제 응답은 SearchByBuildingNameResponse or SearchByFacilityNameResponse
    public static <T> PlaceSearchResponse<T> ofBuilding(T data) {
        return new PlaceSearchResponse<>("BUILDING", data);
    }

    public static <T> PlaceSearchResponse<T> ofFacility(T data) {
        return new PlaceSearchResponse<>("FACILITY", data);
    }
}