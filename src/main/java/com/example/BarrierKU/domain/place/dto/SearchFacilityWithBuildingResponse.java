package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;

public record SearchFacilityWithBuildingResponse(
        @Schema(
                description = "Facilities 객체의 id",
                example = "12"
        )
        long id,
        @Schema(
                description = "편의시설 이름",
                example = "레스티오"
        )
        String name,
        @Schema(
                description = "편의시설 용도",
                example = "CAFE"
        )
        Purpose purpose,
        @Schema(
                description = "편의시설이 속한 건물의 id",
                example = "3"
        )
        long buildingId,
        @Schema(
                description = "편의시설이 속한 건물의 이름",
                example = "공학관"
        )
        String buildingName,
        @Schema(
                description = "건물의 위도",
                example = "37.5665"
        )
        double latitude,
        @Schema(
                description = "건물의 경도",
                example = "126.9780"
        )
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
