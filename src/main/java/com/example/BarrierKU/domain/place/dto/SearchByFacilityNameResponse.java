package com.example.BarrierKU.domain.place.dto;

import java.util.List;

public record SearchByFacilityNameResponse(List<? extends SearchFacilityResponse> facilities) {
}
