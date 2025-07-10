package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.image.DoorImage;
import com.example.BarrierKU.domain.image.SignificantImage;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.indoor.Door;
import com.example.BarrierKU.domain.indoor.Significant;
import com.example.BarrierKU.domain.type.Purpose;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BuildingResponse {
    private Long id;
    private int number;
    private String name;
    private String department;
    private String image;
    private List<DoorInfo> doorInfos;
    private Set<Purpose> facilityPurposes;
    private List<SignificantInfo> significantInfos;

    @Getter
    @AllArgsConstructor
    public static class DoorInfo {
        private Long id;
        private boolean wheelchair;
        private double latitude;
        private double longitude;
        private List<String> imageUrl;
    }

    @Getter
    @AllArgsConstructor
    public static class SignificantInfo {
        private Long id;
        private String description;
        private List<String> imageUrl;
    }

    public BuildingResponse(Building building, List<Door> doors, List<Significant> significants) {
        this.id = building.getId();
        this.number = building.getNumber();
        this.name = building.getName();
        this.department = building.getDepartment();
        this.image = building.getImage();
        this.facilityPurposes = building.getFacilityPurposes();
        this.doorInfos = doors.stream().map(door -> new DoorInfo(door.getId(), door.isWheelchair()
                        , door.getSpot().getY(), door.getSpot().getX(),
                        door.getImages().stream().map(DoorImage::getUrl).collect(Collectors.toList())))
                .collect(Collectors.toList());
        this.significantInfos = (significants != null ? significants : Collections.<Significant>emptyList())
                .stream()
                .map(significant -> new SignificantInfo(
                        significant.getId(),
                        significant.getDescription(),
                        (significant.getImages() != null ? significant.getImages() : Collections
                                .<SignificantImage>emptyList())
                                .stream()
                                .map(SignificantImage::getUrl)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
