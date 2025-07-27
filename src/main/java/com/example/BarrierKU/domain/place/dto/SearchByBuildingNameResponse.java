package com.example.BarrierKU.domain.place.dto;

import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;

public record SearchByBuildingNameResponse(
    @Schema(description = "건물과 해당 건물에 포함된 편의시설 리스트 매핑 정보")
    Map<SearchBuildingResponse, List<SearchFacilityResponse>> buildings) {
}
