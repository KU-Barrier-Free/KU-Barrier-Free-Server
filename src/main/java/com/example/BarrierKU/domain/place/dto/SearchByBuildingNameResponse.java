package com.example.BarrierKU.domain.place.dto;

import java.util.List;
import java.util.Map;

public record SearchByBuildingNameResponse(Map<SearchBuildingResponse, List<SearchFacilityResponse>> buildings) {
}
