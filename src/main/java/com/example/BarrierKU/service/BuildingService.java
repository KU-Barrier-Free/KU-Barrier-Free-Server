package com.example.BarrierKU.service;

import com.example.BarrierKU.domain.Indoor.Building;
import com.example.BarrierKU.dto.response.BuildingResponse;
import com.example.BarrierKU.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingResponse findBuildingById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return BuildingResponse.from(building);
    }
}
