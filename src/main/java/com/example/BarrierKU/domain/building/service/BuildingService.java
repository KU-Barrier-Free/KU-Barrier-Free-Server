package com.example.BarrierKU.domain.building.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.dto.*;
import com.example.BarrierKU.domain.building.repository.RoomRepository;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.indoor.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.BarrierKU.common.response.ResponseCode.BUILDING_NOT_FOUND;
import static com.example.BarrierKU.common.response.ResponseCode.SPACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final RoomRepository roomRepository;

    public BuildingResponse findBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));
        return BuildingResponse.from(building);
    }

    public SpaceResponse getSpaceInfo(Long buildingId, Long spaceId, int type) {
        Room room = roomRepository.findByIdAndBuildingId(spaceId, buildingId)
                .orElseThrow(() -> new BarrierKuException(SPACE_NOT_FOUND));
        return SpaceResponse.of(room, type);
    }

    public SpaceSearchResponse searchSpace(Long buildingId, String keyword) {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));

        List<SpaceSummary> spaces = building.getRooms().stream()
                .filter(room ->
                        room.getRoomName().toLowerCase().contains(keyword.toLowerCase()) || room.getRoomNumber().toLowerCase().contains(keyword.toLowerCase())
                )
                .map(SpaceSummary::from)
                .toList();

        return new SpaceSearchResponse(spaces.size(), spaces);
    }
}
