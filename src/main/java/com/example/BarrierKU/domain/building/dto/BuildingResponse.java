package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.image.DoorImage;
import com.example.BarrierKU.domain.image.SignificantImage;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.indoor.Door;
import com.example.BarrierKU.domain.indoor.Significant;
import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BuildingResponse {
    @Schema(description = "건물 아이디", example = "1")
    private Long id;
    @Schema(description = "건물 번호", example = "2")
    private int number;
    @Schema(description = "건물명", example = "경영관")
    private String name;
    @Schema(description = "건물 담당 부서", example = "경영(전문)대학원, 경영대학")
    private String department;
    @Schema(description = "도면 이미지", example = "image.png")
    private String image;
    @Schema(
            description = "문 정보 리스트",
            example = "[{" +
                    "\"id\": 1," +
                    "\"wheelchair\": true," +
                    "\"imageUrl\": [\"https://example.com/door1.jpg\", \"https://example.com/door2.jpg\"]" +
                    "}, {" +
                    "\"id\": 2," +
                    "\"wheelchair\": false," +
                    "\"imageUrl\": [\"https://example.com/door3.jpg\"]" +
                    "\"spot\": [\"https://example.com/door3.jpg\"]" +
                    "}]"
    )
    private List<DoorInfo> doorInfos;
    @Schema(description = "해당 건물에 존재하는 편의시설 종류", example = "[\"은행\", \"휴게실\", \"카페\"]")
    private Set<String> facilityPurposes;
    @Schema(
            description = "특이사항 리스트",
            example = "[{" +
                    "\"id\": 1," +
                    "\"description\": \"경사로\"," +
                    "\"imageUrl\": [\"https://example.com/significant1.jpg\"]" +
                    "}, {" +
                    "\"id\": 2," +
                    "\"description\": \"계단으로도 이용 가능\"," +
                    "\"imageUrl\": [\"https://example.com/significant2.jpg\", \"https://example.com/significant3.jpg\"]" +
                    "}]"
    )
    private List<SignificantInfo> significantInfos;

    @Getter
    @AllArgsConstructor
    public static class DoorInfo {
        private Long id;
        private boolean wheelchair;
        private List<String> imageUrl;
        private double latitude;
        private double longitude;
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
        this.facilityPurposes = building.getFacilityPurposes().stream()
                .map(Purpose::getValue).collect(Collectors.toSet());
        this.doorInfos = doors.stream().map(door -> new DoorInfo(door.getId(), door.isWheelchair(),
                door.getImages().stream().map(DoorImage::getUrl)
                        .toList(), door.getSpot().getY(), door.getSpot().getX())).toList();
        this.significantInfos = (significants != null ? significants : Collections.<Significant>emptyList())
                .stream()
                .map(significant -> new SignificantInfo(
                        significant.getId(),
                        significant.getDescription(),
                        (significant.getImages() != null ? significant.getImages() : Collections
                                .<SignificantImage>emptyList())
                                .stream()
                                .map(SignificantImage::getUrl)
                                .toList()
                ))
                .toList();
    }
}
