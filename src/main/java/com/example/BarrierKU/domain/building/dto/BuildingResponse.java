package com.example.BarrierKU.domain.building.dto;


import com.example.BarrierKU.domain.door.dto.DoorInfo;
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
    @Schema(description = "문 정보 리스트",
            example = "[{" +
                    "\"id\": 1," +
                    "\"wheelchair\": true," +
                    "\"imageUrl\": [\"https://example.com/door1.jpg\", \"https://example.com/door2.jpg\"]," +
                    "\"latitude\": 37.12345," +
                    "\"longitude\": 127.98765" +
                    "\"label\": A" +
                    "}," +
                    "{" +
                    "\"id\": 2," +
                    "\"wheelchair\": false," +
                    "\"imageUrl\": [\"https://example.com/door3.jpg\"]," +
                    "\"latitude\": 37.54321," +
                    "\"longitude\": 127.11111" +
                    "\"label\": B" +
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
            description = "층별 공간 정보",
            example = "[\n" +
                    "  {\n" +
                    "    \"drawings\": [\"b1_floorplan.png\"],\n" +
                    "    \"purposes\": [\"은행\", \"휴게실\", \"카페\"],\n" +
                    "    \"spaceSummaries\": [\n" +
                    "      {\n" +
                    "        \"id\": 101,\n" +
                    "        \"roomNumber\": \"B101\",\n" +
                    "        \"roomName\": \"세미나실\",\n" +
                    "        \"roomComment\": \"회의 및 세미나용 공간\",\n" +
                    "        \"imageUrls\": [\"seminar_room1.png\", \"seminar_room2.png\"],\n" +
                    "        \"lecture\": false\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 102,\n" +
                    "        \"roomNumber\": \"B102\",\n" +
                    "        \"roomName\": \"컴퓨터실\",\n" +
                    "        \"roomComment\": \"수업 및 실습용 컴퓨터실\",\n" +
                    "        \"imageUrls\": [\"computer_room1.png\"],\n" +
                    "        \"lecture\": true\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"floor\": \"B1\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"drawings\": [\"1f_floorplan.png\"],\n" +
                    "    \"purposes\": [\"카페\", \"은행\"],\n" +
                    "    \"spaceSummaries\": [\n" +
                    "      {\n" +
                    "        \"id\": 201,\n" +
                    "        \"roomNumber\": \"101\",\n" +
                    "        \"roomName\": \"카페테리아\",\n" +
                    "        \"roomComment\": \"학생용 식당\",\n" +
                    "        \"imageUrls\": [\"cafeteria1.png\"],\n" +
                    "        \"lecture\": false\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"floor\": \"1\"\n" +
                    "  }\n" +
                    "]"
    )
    private List<FloorResponse> floorList;
    @Schema(description = "위도", example = "37.54321")
    private double latitude;
    @Schema(description = "경도", example = "127.11111")
    private double longitude;

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

    public BuildingResponse(Long buildingId, int number, String name, String department, String image, double latitude, double longitude, Set<String> purposes, List<Door> doors, List<Significant> significants, List<FloorResponse> floorList) {
        this.id = buildingId;
        this.number = number;
        this.name = name;
        this.department = department;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.facilityPurposes = purposes;
        this.doorInfos = doors.stream().map(door -> new DoorInfo(door)).toList();
        this.significantInfos = significants.stream().map(significant -> new SignificantInfo(significant)).toList();
        this.floorList = floorList;
    }
}
