package com.example.BarrierKU.domain.facility.dto;

import com.example.BarrierKU.domain.type.Purpose;
import org.locationtech.jts.geom.Point;

public record FacilitiesResponse(
        Long facilityId,
        String facilityName,
        String purpose,
        BuildingInfo buildingInfo
) {
    public FacilitiesResponse(Long facilityId, String facilityName, Purpose purpose, Long buildingId, Point spot) {
        this(facilityId, facilityName, purpose.name(), new BuildingInfo(buildingId, spot.getY(), spot.getX()));
    }

    public record BuildingInfo(
            Long id,
            double latitude,
            double longitude
    ) {}
}
