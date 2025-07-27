package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Building;
import io.swagger.v3.oas.annotations.media.Schema;

public record SearchBuildingResponse(
        @Schema(description = "건물 ID", example = "3")
        long id,
        @Schema(description = "건물 이름", example = "공학관")
        String name,
        @Schema(description = "건물의 위도", example = "37.5821")
        double latitude,
        @Schema(description = "건물의 경도", example = "127.0095")
        double longitude
) {
    public SearchBuildingResponse(Building building) {
        this(building.getId(), building.getName(), building.getSpot().getY(), building.getSpot().getX());
    }
}
