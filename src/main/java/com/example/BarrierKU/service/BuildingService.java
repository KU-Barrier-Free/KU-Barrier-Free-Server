package com.example.BarrierKU.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.Indoor.Building;
import com.example.BarrierKU.dto.response.BuildingResponse;
import com.example.BarrierKU.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.BarrierKU.common.response.ResponseCode.BUILDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingResponse findBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));
        return BuildingResponse.from(building);
    }
}
