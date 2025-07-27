package com.example.BarrierKU.domain.place.dto;

import com.example.BarrierKU.domain.indoor.Building;

public record SearchBuildingResponse(long id, String name, double latitude, double longitude) {
    public SearchBuildingResponse(Building building) {
        this(building.getId(), building.getName(), building.getSpot().getY(), building.getSpot().getX());
    }
}
