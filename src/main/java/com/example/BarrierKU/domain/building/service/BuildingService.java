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
import com.example.BarrierKU.domain.type.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        Set<String> purposes = building.getFacilities().stream()
                .map(facility -> facility.getPurpose().getValue()).collect(Collectors.toSet());
        List<Door> doors = building.getDoors();
        List<Significant> significants = building.getSignificants();
        Map<String, List<Room>> roomsByFloor = groupedByFloor(building);
        Map<String, FloorResponse> floorMap = getFloorResponseMap(roomsByFloor, building);
        return new BuildingResponse(id,building.getNumber(),building.getName(), building.getDepartment(),building.getImage(),
                purposes, doors, significants, floorMap);
    }

    private Map<String, FloorResponse> getFloorResponseMap(Map<String, List<Room>> roomsByFloor, Building building) {
        return roomsByFloor.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    String floor = entry.getKey();
                    List<Room> rooms = entry.getValue();

                    List<String> drawings = getDrawings(floor, building);
                    Set<String> purposesFloor = getPurposes(floor, building);
                    List<SpaceSummary> summaries = getSpaceSummaries(floor, building);

                    return new FloorResponse(drawings, purposesFloor, summaries);
                }
        ));
    }

    public FloorResponse getFloorInfo(Long id, String targetFloor) {
        Building building = getBuilding(id);
        List<String> drawings = getDrawings(targetFloor, building);
        Set<String> purposes = getPurposes(targetFloor, building);
        List<SpaceSummary> spaceSummaries = getSpaceSummaries(targetFloor, building);

        return new FloorResponse(drawings, purposes, spaceSummaries);
    }

    private List<SpaceSummary> getSpaceSummaries(String targetFloor, Building building) {
        return building.getRooms().stream().filter(room -> room.getFloor().equals(targetFloor))
                .map(room -> new SpaceSummary(room.getId(), room.getRoomNumber(), room.getRoomName()
                        , room.getRoomComment(), room.getRoomImages().stream().map(RoomImage::getUrl).collect(Collectors.toList())
                        , room.isLecture())).collect(Collectors.toList());
    }

    private Set<String> getPurposes(String targetFloor, Building building) {
        return building.getFacilities().stream()
                .filter(facility -> facility.getFloor().equals(targetFloor))
                .map(facility -> facility.getPurpose().getValue()).collect(Collectors.toSet());
    }

    private List<String> getDrawings(String targetFloor, Building building) {
        return building.getDrawings().stream()
                .filter(drawing -> drawing.getFloor().equals(targetFloor))
                .map(drawing -> drawing.getImage())
                .toList();
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

    private Map<String, List<Room>> groupedByFloor(Building building){
        return building.getRooms().stream().collect(Collectors.groupingBy(Room::getFloor));
    }
}
