package com.example.BarrierKU.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "건물명으로 검색된 편의시설 리스트 응답",
        example = """
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
        """)
public class SearchByBuildingNameResponse {

    @Schema(description = "건물과 해당 건물에 포함된 편의시설 리스트")
    private List<BuildingFacilityGroup> buildingFacilityGroups;

    public void putFacility(SearchBuildingResponse building, List<SearchFacilityResponse> facilities) {
        BuildingFacilityGroup buildingFacilityGroup = new BuildingFacilityGroup(building, facilities);
        this.buildingFacilityGroups.add(buildingFacilityGroup);
    }

    @Schema(description = "건물-편의시설 그룹")
    static class BuildingFacilityGroup{
        @Schema(description = "건물 정보")
        private SearchBuildingResponse building;
        @io.swagger.v3.oas.annotations.media.ArraySchema(arraySchema = @Schema(description = "해당 건물의 편의시설 목록"))
        private List<SearchFacilityResponse> facilities;

        public BuildingFacilityGroup(SearchBuildingResponse building, List<SearchFacilityResponse> facilities) {
            this.building = building;
            this.facilities = facilities;
        }
    }
}
