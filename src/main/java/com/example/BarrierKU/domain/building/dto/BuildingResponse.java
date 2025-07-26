package com.example.BarrierKU.domain.building.dto;


import com.example.BarrierKU.domain.image.DoorImage;
import com.example.BarrierKU.domain.image.SignificantImage;
import com.example.BarrierKU.domain.indoor.Door;
import com.example.BarrierKU.domain.indoor.Significant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
    @Schema(description = "건물 이미지", example = "image.png")
    private String image;
    @Schema(
            description = "문 정보 리스트",
            example = "[" +
                    "{" +
                    "\"id\": 1," +
                    "\"wheelchair\": true," +
                    "\"imageUrl\": [\"https://example.com/door1.jpg\", \"https://example.com/door2.jpg\"]," +
                    "\"latitude\": 37.12345," +
                    "\"longitude\": 127.98765" +
                    "}," +
                    "{" +
                    "\"id\": 2," +
                    "\"wheelchair\": false," +
                    "\"imageUrl\": [\"https://example.com/door3.jpg\"]," +
                    "\"latitude\": 37.54321," +
                    "\"longitude\": 127.11111" +
                    "}" +
                    "]"
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
    @Schema(
            description = "층별 공간 정보 (key: 층 이름 예: 1, B1)",
            example = "{" +
                    "\"B1\": {" +
                    "\"drawings\": [\"image.png\"]," +
                    "\"purposes\": [\"은행\", \"휴게실\", \"카페\"]," +
                    "\"spaceSummaries\": [" +
                    "{" +
                    "\"id\": 1," +
                    "\"roomNumber\": \"104-1호\"," +
                    "\"roomName\": \"강의실\"," +
                    "\"comment\": \"원형책상\"," +
                    "\"roomImages\": [\"https://example.com/image1.jpg\"]," +
                    "\"isLecture\": true" +
                    "}" +
                    "]" +
                    "}" +
                    "}"
    )
    private Map<String, FloorResponse> floorMap;

    @Getter
    @AllArgsConstructor
    public static class DoorInfo {
        private Long id;
        private boolean wheelchair;
        private List<String> imageUrl;
        private double latitude;
        private double longitude;

        private DoorInfo(Door door) {
            id = door.getId();
            wheelchair = door.isWheelchair();
            imageUrl = door.getImages().stream().map(DoorImage::getUrl).toList();
            latitude = door.getSpot().getY();
            longitude = door.getSpot().getX();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class SignificantInfo {
        private Long id;
        private String description;
        private List<String> imageUrl;

        private SignificantInfo(Significant significant) {
            id = significant.getId();
            description = significant.getDescription();
            imageUrl = (significant.getImages() != null ? significant.getImages() : Collections
                    .<SignificantImage>emptyList()).stream().map(SignificantImage::getUrl).toList();
        }
    }

    public BuildingResponse(Long buildingId, int number, String name, String department, String image, Set<String> purposes, List<Door> doors, List<Significant> significants, Map<String, FloorResponse> floorMap) {
        this.id = buildingId;
        this.number = number;
        this.name = name;
        this.department = department;
        this.image = image;
        this.facilityPurposes = purposes;
        this.doorInfos = doors.stream().map(door -> new DoorInfo(door)).toList();
        this.significantInfos = significants.stream().map(significant -> new SignificantInfo(significant)).toList();
        this.floorMap = floorMap;
    }
}
