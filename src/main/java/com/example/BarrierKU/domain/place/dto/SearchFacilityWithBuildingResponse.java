package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchFacilityWithBuildingResponse extends SearchFacilityResponse {
    @Schema(
            description = "편의시설이 속한 건물의 id",
            example = "3"
    )
    private final long buildingId;

    @Schema(
            description = "편의시설이 속한 건물의 이름",
            example = "공학관"
    )
    private final String buildingName;

    @Schema(
            description = "건물의 위도",
            example = "37.5665"
    )
    private final double latitude;

    @Schema(
            description = "건물의 경도",
            example = "126.9780"
    )
    private final double longitude;

    public SearchFacilityWithBuildingResponse(Facilities facilities) {
        super(facilities.getId(), facilities.getName(), facilities.getPurpose());
        this.buildingId = facilities.getBuilding().getId();
        this.buildingName = facilities.getBuilding().getName();
        this.latitude = facilities.getBuilding().getSpot().getY();
        this.longitude = facilities.getBuilding().getSpot().getX();
    }

}
