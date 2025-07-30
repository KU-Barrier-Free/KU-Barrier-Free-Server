package com.example.BarrierKU.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "장소 검색 결과 응답")
public record PlaceSearchResponse<T>(
        @Schema(description = "응답 타입", example = "BUILDING") String type,
        @Schema(
                description = "응답 데이터. type이 'BUILDING'이면 SearchByBuildingNameResponse, 'FACILITY'이면 SearchByFacilityNameResponse 를 반환하는 형식입니다.",
                oneOf = {SearchByBuildingNameResponse.class, SearchByFacilityNameResponse.class}
        ) T data) {
    public static <T> PlaceSearchResponse<T> ofBuilding(T data) {
        return new PlaceSearchResponse<>("BUILDING", data);
    }

    public static <T> PlaceSearchResponse<T> ofFacility(T data) {
        return new PlaceSearchResponse<>("FACILITY", data);
    }
}