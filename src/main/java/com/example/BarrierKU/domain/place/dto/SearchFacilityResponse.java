package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;

public record SearchFacilityResponse(long id, String name, Purpose purpose) {
    public SearchFacilityResponse(Facilities facilities) {
        this(facilities.getId(), facilities.getName(), facilities.getPurpose());
    }
}
