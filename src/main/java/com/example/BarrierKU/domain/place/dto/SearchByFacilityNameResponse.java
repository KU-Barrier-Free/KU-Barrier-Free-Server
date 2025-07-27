package com.example.BarrierKU.domain.place.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record SearchByFacilityNameResponse(
    @Schema(description = "편의시설 정보 리스트")
    List<? extends SearchFacilityResponse> facilities
) {
    public SearchByFacilityNameResponse(List<? extends SearchFacilityResponse> facilities) {
        this.facilities = facilities;
    }
}
