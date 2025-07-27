package com.example.BarrierKU.domain.place.dto;

public record PlaceSearchResponse<T>(String type, T data) {
    // data : 실제 응답은 SearchByBuildingNameResponse or SearchByFacilityNameResponse
    public static <T> PlaceSearchResponse<T> ofBuilding(T data) {
        return new PlaceSearchResponse<>("BUILDING", data);
    }

    public static <T> PlaceSearchResponse<T> ofFacility(T data) {
        return new PlaceSearchResponse<>("FACILITY", data);
    }
}