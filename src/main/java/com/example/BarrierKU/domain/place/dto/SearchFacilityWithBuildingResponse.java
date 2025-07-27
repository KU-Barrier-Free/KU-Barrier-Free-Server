package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;

public record SearchFacilityWithBuildingResponse(
        long id,
        String name,
        Purpose purpose,
        long buildingId,
        String buildingName,
        double latitude,
        double longitude
) {
    public SearchFacilityWithBuildingResponse(Facilities facilities) {
        this(
                facilities.getId(),
                facilities.getName(),
                facilities.getPurpose(),
                facilities.getBuilding().getId(),
                facilities.getBuilding().getName(),
                facilities.getBuilding().getSpot().getY(),
                facilities.getBuilding().getSpot().getX()
        );
    }
}
