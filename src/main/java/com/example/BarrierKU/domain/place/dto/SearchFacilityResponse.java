package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchFacilityResponse {
    @Schema(description = "시설 ID", example = "12")
    private long id;

    @Schema(description = "시설 이름", example = "레스티오")
    private String name;

    @Schema(description = "시설 용도", example = "CAFE")
    private Purpose purpose;

    public SearchFacilityResponse(long id, String name, Purpose purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public SearchFacilityResponse(Facilities facilities) {
        this(facilities.getId(), facilities.getName(), facilities.getPurpose());
    }
}
