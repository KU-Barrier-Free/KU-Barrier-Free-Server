package com.example.BarrierKU.domain.place.service;

import com.example.BarrierKU.domain.building.service.BuildingService;
import com.example.BarrierKU.domain.facility.service.FacilityService;
import com.example.BarrierKU.domain.place.dto.PlaceSearchResponse;
import com.example.BarrierKU.domain.place.dto.SearchBuildingResponse;
import com.example.BarrierKU.domain.place.dto.SearchFacilityWithBuildingResponse;
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

    public PlaceSearchResponse getPlaces(String searchWord) {
        log.debug("[getPlaces] 검색어 = {}", searchWord);
        List<SearchBuildingResponse> buildings = buildingService.getBuildings(searchWord);

        if (!buildings.isEmpty()) {
            // 1. 만약 건물이 하나라도 조회되는 경우
            log.info("[getPlaces] 검색어를 포함한 건물이름 존재 = {}",
                    buildings.stream().map(SearchBuildingResponse::name).collect(Collectors.joining(", ")));
            PlaceSearchResponse response = PlaceSearchResponse.ofBuildings(buildings);
            buildings.forEach(building -> {
                List<SearchFacilityWithBuildingResponse> facilityResponses = facilityService.getFacilitiesByBuildingId(building.id()).stream()
                        .map(facilities -> new SearchFacilityWithBuildingResponse(facilities, building))
                        .toList();
                response.putFacilities(facilityResponses);
            });
            return response;
        }
        // 2. 만약 건물이 하나도 조회되지 않으면, 키워드를 포함하는 이름을 갖는 모든 편의시설을 반환
        List<SearchFacilityWithBuildingResponse> facilities = facilityService.getFacilitiesByName(searchWord).stream()
                .map(SearchFacilityWithBuildingResponse::new)
                .toList();
        log.debug("[getPlaces] 편의시설 이름 조회 결과 수 = {}", facilities.size());
        return PlaceSearchResponse.ofFacilities(facilities);
    }
}
