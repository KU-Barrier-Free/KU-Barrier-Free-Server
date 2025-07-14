package com.example.BarrierKU.domain.floor.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.floor.dto.FloorResponse;
import com.example.BarrierKU.domain.floor.dto.SpaceSummary;
import com.example.BarrierKU.domain.image.Image;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.type.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.BarrierKU.common.response.ResponseCode.BUILDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final BuildingRepository buildingRepository;

    public FloorResponse getFloorInfo(Long id, String targetFloor) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));
        String floorPlan = building.getFloorPlans().stream()
                .filter(plan -> plan.getFloor().equals(targetFloor))
                .findFirst()
                .map(plan -> plan.getImage())
                .orElse(null);
        Set<Purpose> purposes = building.getFacilities().stream()
                .filter(facility -> facility.getFloor().equals(targetFloor))
                .map(facility -> facility.getPurpose()).collect(Collectors.toSet());
        List<SpaceSummary> spaceSummaries = building.getRooms().stream().filter(room -> room.getFloor().equals(targetFloor))
                .map(room -> new SpaceSummary(room.getId(), room.getRoomNumber(), room.getRoomName()
                        , room.getRoomComment(), room.getImages().stream().map(Image::getUrl).collect(Collectors.toList())
                        , room.isLecture())).collect(Collectors.toList());

        return new FloorResponse(floorPlan, purposes, spaceSummaries);
    }
}
