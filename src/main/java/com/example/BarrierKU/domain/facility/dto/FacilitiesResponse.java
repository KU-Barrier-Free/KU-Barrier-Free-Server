package com.example.BarrierKU.domain.facility.dto;

import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;
import org.locationtech.jts.geom.Point;

public record FacilitiesResponse(
        @Schema(description = "편의시설 ID", example = "1")
        Long facilityId,
        @Schema(description = "편의시설 이름", example = "카페 레스티오")
        String facilityName,
        @Schema(description = "편의시설 용도", example = "CAFE")
        String purpose,
        @Schema(description = "건물 정보")
        BuildingInfo buildingInfo
) {
    public FacilitiesResponse(Long facilityId, String facilityName, Purpose purpose, Long buildingId, Point spot) {
        this(facilityId, facilityName, purpose.name(), new BuildingInfo(buildingId, spot.getY(), spot.getX()));
    }

    public record BuildingInfo(
            @Schema(description = "건물 ID", example = "2")
            Long id,
            @Schema(description = "위도", example = "37.541")
            double latitude,
            @Schema(description = "경도", example = "127.078")
            double longitude
    ) {}
}
