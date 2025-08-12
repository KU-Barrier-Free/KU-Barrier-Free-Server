package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchFacilityWithBuildingResponse {
    @Schema(description = "시설 ID", example = "12")
    private long id;

    @Schema(description = "시설 이름", example = "레스티오")
    private String name;

    @Schema(description = "시설 용도", example = "CAFE")
    private Purpose purpose;

    @Schema(description = "편의시설이 속한 건물의 id", example = "3")
    private final long buildingId;

    @Schema(description = "편의시설이 속한 건물의 이름", example = "공학관")
    private final String buildingName;

    @Schema(description = "건물의 위도", example = "37.5665")
    private final double latitude;

    @Schema(description = "건물의 경도", example = "126.9780")
    private final double longitude;

    public SearchFacilityWithBuildingResponse(Facilities facilities) {
        this.id = facilities.getId();
        this.name = facilities.getName();
        this.purpose = facilities.getPurpose();
        this.buildingId = facilities.getBuilding().getId();
        this.buildingName = facilities.getBuilding().getName();
        this.latitude = facilities.getBuilding().getSpot().getY();
        this.longitude = facilities.getBuilding().getSpot().getX();
    }

    public SearchFacilityWithBuildingResponse(Facilities facilities, SearchBuildingResponse building) {
        this.id = facilities.getId();
        this.name = facilities.getName();
        this.purpose = facilities.getPurpose();
        this.buildingId = building.id();
        this.buildingName = building.name();
        this.latitude = building.latitude();
        this.longitude = building.longitude();
    }
}
