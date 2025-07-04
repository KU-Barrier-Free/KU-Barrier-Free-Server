package com.example.BarrierKU.service;

import com.example.BarrierKU.domain.Indoor.Building;
import com.example.BarrierKU.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public Building findBuildingById(Long id) {
        return buildingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }
}
