package com.example.BarrierKU.domain.building.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.dto.FloorResponse;
import com.example.BarrierKU.domain.building.dto.SpaceResponse;
import com.example.BarrierKU.domain.building.dto.SpaceSummary;
import com.example.BarrierKU.domain.building.repository.RoomRepository;
import com.example.BarrierKU.domain.image.RoomImage;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.building.dto.BuildingResponse;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.indoor.Door;
import com.example.BarrierKU.domain.indoor.Room;
import com.example.BarrierKU.domain.indoor.Significant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.BarrierKU.common.response.ResponseCode.BUILDING_NOT_FOUND;
import static com.example.BarrierKU.common.response.ResponseCode.SPACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final RoomRepository roomRepository;

    public BuildingResponse findBuildingById(Long id) {
        Building building = getBuilding(id);
        List<Door> doors = building.getDoors();
        List<Significant> significants = building.getSignificants();
        return new BuildingResponse(building, doors, significants);
    }
    public FloorResponse getFloorInfo(Long id, String targetFloor) {
        Building building = getBuilding(id);
        List<String> drawings = building.getDrawings().stream()
                .filter(drawing -> drawing.getFloor().equals(targetFloor))
                .map(drawing -> drawing.getImage())
                .toList();
        Set<String> purposes = building.getFacilities().stream()
                .filter(facility -> facility.getFloor().equals(targetFloor))
                .map(facility -> facility.getPurpose().getValue()).collect(Collectors.toSet());
        List<SpaceSummary> spaceSummaries = building.getRooms().stream().filter(room -> room.getFloor().equals(targetFloor))
                .map(room -> new SpaceSummary(room.getId(), room.getRoomNumber(), room.getRoomName()
                        , room.getRoomComment(), room.getRoomImages().stream().map(RoomImage::getUrl).collect(Collectors.toList())
                        , room.isLecture())).collect(Collectors.toList());

        return new FloorResponse(drawings, purposes, spaceSummaries);
    }

    public Building getBuilding(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));
    }

    public SpaceResponse getSpaceInfo(Long buildingId, Long spaceId, int type) {
        Room room = roomRepository.findByIdAndBuildingId(spaceId, buildingId)
                .orElseThrow(() -> new BarrierKuException(SPACE_NOT_FOUND));
        return SpaceResponse.of(room, type);
    }
}
