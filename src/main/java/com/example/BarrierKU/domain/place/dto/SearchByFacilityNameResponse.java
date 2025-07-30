package com.example.BarrierKU.domain.place.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "편의시설명으로 검색된 편의시설 리스트 응답",
    example = """
    {
      "facilities": [
        {
          "id": 12,
          "name": "카페 레스티오",
          "purpose": "CAFE",
          "buildingId": 2,
          "buildingName": "경영관",
          "latitude": 37.541,
          "longitude": 127.079
        },
        {
          "id": 13,
          "name": "카페 레스티오",
          "purpose": "CAFE",
          "buildingId": 21,
          "buildingName": "공학관",
          "latitude": 37.5415,
          "longitude": 127.0801
        },
        {
          "id": 14,
          "name": "카페 레스티오",
          "purpose": "CAFE",
          "buildingId": 12,
          "buildingName": "동물생명과학관",
          "latitude": 37.5401,
          "longitude": 127.0823
        },
        {
          "id": 15,
          "name": "카페 레스티오",
          "purpose": "CAFE",
          "buildingId": 14,
          "buildingName": "산학협동관",
          "latitude": 37.5423,
          "longitude": 127.0788
        },
        {
          "id": 16,
          "name": "카페 레스티오",
          "purpose": "CAFE",
          "buildingId": 9,
          "buildingName": "상허기념도서관",
          "latitude": 37.5412,
          "longitude": 127.0799
        },
        {
          "id": 17,
          "name": "카페 레스티오",
          "purpose": "CAFE",
          "buildingId": 5,
          "buildingName": "예술문화관",
          "latitude": 37.5409,
          "longitude": 127.0803
        }
      ]
    }
    """
)
public record SearchByFacilityNameResponse(
        @Schema(description = "편의시설 정보 리스트")
        List<? extends SearchFacilityResponse> facilities
) {
    public SearchByFacilityNameResponse(List<? extends SearchFacilityResponse> facilities) {
        this.facilities = facilities;
    }
}
