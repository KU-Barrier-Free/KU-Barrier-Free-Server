package com.example.BarrierKU.domain.facility.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.facility.dto.FacilitiesResponse;
import com.example.BarrierKU.domain.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.BarrierKU.common.response.ResponseCode.FACILITY_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public FacilitiesResponse getFacility(Long facilityId) {
        Facilities facility = facilityRepository.findWithBuildingById(facilityId)
                .orElseThrow(() -> new BarrierKuException(FACILITY_NOT_FOUND));
        Building building = facility.getBuilding();
        return new FacilitiesResponse(
                facility.getId(),
                facility.getName(),
                facility.getPurpose(),
                building.getId(),
                building.getSpot()
        );
    }
}
