package com.example.BarrierKU.domain.facility.dto;

import com.example.BarrierKU.domain.type.Purpose;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilitiesResponse {
    private Long facilityId;
    private String facilityName;
    private String purpose;
    private BuildingInfo buildingInfo;

    @Getter
    @AllArgsConstructor
    public static class BuildingInfo {
        private Long id;
        private double latitude;
        private double longitude;
    }

    public FacilitiesResponse(Long facilityId, String facilityName, Purpose purpose, Long buildingId, Point spot) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.purpose = purpose.name();
        this.buildingInfo = new BuildingInfo(
                buildingId,
                spot.getY(),
                spot.getX()
        );
    }
}
