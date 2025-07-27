package com.example.BarrierKU.domain.place.service;

import com.example.BarrierKU.domain.building.service.BuildingService;
import com.example.BarrierKU.domain.facility.service.FacilityService;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.place.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {
    private final FacilityService facilityService;
    private final BuildingService buildingService;

    public PlaceSearchResponse getPlaces(String searchWord, double latitude, double longitude) {
        log.info("[getPlaces] 검색어 = {}", searchWord);
        List<SearchBuildingResponse> buildings = buildingService.getBuildings(searchWord);
        if (!buildings.isEmpty()) {
            // 1. 만약 건물이 하나라도 조회된다.
            log.info("[getPlaces] 검색어를 포함한 건물이름 존재 = {}",
                    buildings.stream().map(SearchBuildingResponse::getName).collect(Collectors.joining(", ")));
            SearchByBuildingNameResponse searchByBuildingNameResponse = new SearchByBuildingNameResponse();
            buildings.forEach(building -> {
                List<SearchFacilityResponse> facilities = facilityService.getFacilitiesByBuildingId(building.getId());
                searchByBuildingNameResponse.putFacility(building, facilities);
            });
            return PlaceSearchResponse.ofBuilding(searchByBuildingNameResponse);
        }
        // 2. 만약 건물이 하나도 조회되지 않으면, 해당 이름을 포함하는 편의시설을 모두 조회해서 반환하기
        List<SearchFacilityWithBuildingResponse> facilities = facilityService.getFacilitiesByName(searchWord);
        log.info("[getPlaces] 편의시설 이름 조회 결과 수 = {}", facilities.size());
        SearchByFacilityNameResponse response = new SearchByFacilityNameResponse(facilities);
        return PlaceSearchResponse.ofFacility(response);
    }
}
