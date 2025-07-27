package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;

public record SearchFacilityResponse(
    @Schema(description = "시설 ID", example = "12") long id,
    @Schema(description = "시설 이름", example = "레스티오") String name,
    @Schema(description = "시설 용도", example = "CAFE") Purpose purpose) {
    public SearchFacilityResponse(Facilities facilities) {
        this(facilities.getId(), facilities.getName(), facilities.getPurpose());
    }
}
