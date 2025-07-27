package com.example.BarrierKU.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceSearchResponse<T>(
        @Schema(description = "응답 타입", example = "BUILDING") String type,
        @Schema(description = "응답 데이터", example = """
        {
          "buildingFacilityGroups": [
            {
              "building": {
                "id": 3,
                "name": "공학관",
                "latitude": 37.5821,
                "longitude": 127.0095
              },
              "facilities": [
                {
                  "id": 12,
                  "name": "레스티오",
                  "purpose": "CAFE"
                },
                {
                  "id": 13,
                  "name": "편의점",
                  "purpose": "STORE"
                }
              ]
            },
            {
              "building": {
                "id": 4,
                "name": "신공학관",
                "latitude": 37.5829,
                "longitude": 127.0101
              },
              "facilities": [
                {
                  "id": 15,
                  "name": "학생휴게실",
                  "purpose": "LOUNGE"
                }
              ]
            }
          ]
        }
        """) T data) {
    // data : 실제 응답은 SearchByBuildingNameResponse or SearchByFacilityNameResponse
    public static <T> PlaceSearchResponse<T> ofBuilding(T data) {
        return new PlaceSearchResponse<>("BUILDING", data);
    }

    public static <T> PlaceSearchResponse<T> ofFacility(T data) {
        return new PlaceSearchResponse<>("FACILITY", data);
    }
}