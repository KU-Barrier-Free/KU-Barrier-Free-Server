package com.example.BarrierKU.domain.building.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.dto.*;
import com.example.BarrierKU.domain.building.repository.RoomRepository;
import com.example.BarrierKU.domain.door.dto.DoorInfo;
import com.example.BarrierKU.domain.building.dto.BuildingInfoResponse;
import com.example.BarrierKU.domain.image.Drawing;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.building.dto.BuildingResponse;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.indoor.Door;
import com.example.BarrierKU.domain.indoor.Room;
import com.example.BarrierKU.domain.indoor.Significant;
import com.example.BarrierKU.domain.place.dto.SearchBuildingResponse;
import com.example.BarrierKU.domain.type.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.BarrierKU.common.response.ResponseCode.BUILDING_NOT_FOUND;
import static com.example.BarrierKU.common.response.ResponseCode.SPACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final RoomRepository roomRepository;

    public BuildingInfoResponse getBuildingInfo(Long buildingId){
        Building building = getBuilding(buildingId);
        Set<String> purposes = getPurposes(building);
        List<DoorInfo> doorInfos = getDoorInfos(building);
        return new BuildingInfoResponse(buildingId, building.getNumber(), building.getName(), building.isLecture(),
                doorInfos, purposes, building.getSpot().getY(), building.getSpot().getX());
    }

    public BuildingResponse findBuildingById(Long id) {
        Building building = getBuilding(id);
        Set<String> purposes = getPurposes(building);
        List<DoorInfo> doorInfos = getDoorInfos(building);
        List<Significant> significants = building.getSignificants();
        Map<String, List<Room>> roomsByFloor = groupedByFloor(building);
        List<FloorResponse> floorList = getFloorResponseList(roomsByFloor, building);
        List<FloorResponse> sortedFloorList = floorList.stream().sorted(Comparator.comparingInt(response -> {
                    if (response.floor().startsWith("B")) {
                        return -Integer.parseInt(response.floor().substring(1));
                    } else {
                        return Integer.parseInt(response.floor());
                    }
                })).toList();
        return new BuildingResponse(id, building.getNumber(), building.getName(),
                building.getDepartment(), building.getImage(), building.isLecture(),building.getSpot().getY(), building.getSpot().getX(),
                purposes, doorInfos, significants, sortedFloorList);
    }

    public SpaceResponse getSpaceInfo(Long buildingId, Long spaceId, int type) {
        Room room = roomRepository.findByIdAndBuildingId(spaceId, buildingId)
                .orElseThrow(() -> new BarrierKuException(SPACE_NOT_FOUND));
        return SpaceResponse.of(room, type);
    }

    private List<FloorResponse> getFloorResponseList(Map<String, List<Room>> roomsByFloor, Building building) {
        return roomsByFloor.entrySet().stream().map(
                entry -> {
                    String floor = entry.getKey();

                    List<String> drawings = getDrawings(floor, building);
                    Set<String> purposesFloor = getPurposes(floor, building);
                    List<SpaceSummary> summaries = getSpaceSummaries(floor, building);

                     return new FloorResponse(drawings, purposesFloor, summaries, floor);
                }).toList();
    }

    public SpaceSearchResponse searchSpace(Long buildingId, String keyword) {
        Building building = getBuilding(buildingId);

        List<SpaceSummary> spaces = building.getRooms().stream()
                .filter(room ->
                        room.getRoomName().toLowerCase().contains(keyword.toLowerCase()) || room.getRoomNumber().toLowerCase().contains(keyword.toLowerCase())
                )
                .sorted(new RoomNumberComparator())
                .map(SpaceSummary::from)
                .toList();

        return new SpaceSearchResponse(spaces.size(), spaces);
    }

    private Building getBuilding(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));
    }

    private List<SpaceSummary> getSpaceSummaries(String targetFloor, Building building) {
        return building.getRooms().stream()
                .filter(room -> room.getFloor().equals(targetFloor))
                .sorted(new RoomNumberComparator())
                .map(SpaceSummary::from).toList();
    }

    private List<DoorInfo> getDoorInfos(Building building) {
        return building.getDoors().stream()
                .sorted(Comparator.comparing(Door::getLabel))
                .map(DoorInfo::new).toList();
    }

    private List<String> getDrawings(String targetFloor, Building building) {
        return building.getDrawings().stream()
                .filter(drawing -> drawing.getFloor().equals(targetFloor))
                .map(Drawing::getImage)
                .toList();
    }

    private Set<String> getPurposes(String targetFloor, Building building) {
        return building.getFacilities().stream()
                .filter(facility -> facility.getFloor().equals(targetFloor))
                .map(facility -> facility.getPurpose().getValue()).collect(Collectors.toSet());
    }

    private Set<String> getPurposes(Building building) {
        return building.getFacilityPurposes().stream().map(Purpose::getValue).collect(Collectors.toSet());
    }

    private Map<String, List<Room>> groupedByFloor(Building building) {
        return building.getRooms().stream().collect(Collectors.groupingBy(Room::getFloor));
    }

    public List<SearchBuildingResponse> getBuildings(String searchWord) {
        List<Building> buildings = buildingRepository.findByNameContainingIgnoreCase(searchWord);
        return buildings.stream()
                .map(SearchBuildingResponse::new)
                .toList();
    }
}
