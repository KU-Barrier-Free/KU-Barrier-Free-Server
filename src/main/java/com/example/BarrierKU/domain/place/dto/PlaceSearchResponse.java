package com.example.BarrierKU.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "장소 검색 결과 응답")
public record PlaceSearchResponse(
        List<SearchBuildingResponse> buildings,
        List<SearchFacilityWithBuildingResponse> facilities) {

    public static PlaceSearchResponse ofBuildings(List<SearchBuildingResponse> buildings) {
        return new PlaceSearchResponse(buildings, List.of());
    }

    public static PlaceSearchResponse ofFacilities(List<SearchFacilityWithBuildingResponse> facilities) {
        return new PlaceSearchResponse(List.of(), facilities);
    }

    public PlaceSearchResponse() {
        this(List.of(), List.of());
    }


    public void putFacilities(List<SearchFacilityWithBuildingResponse> newFacilities) {
        facilities.addAll(newFacilities);
    }

}
