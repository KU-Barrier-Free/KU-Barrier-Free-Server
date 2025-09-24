package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.door.dto.DoorInfo;
import com.example.BarrierKU.domain.image.SignificantImage;
import com.example.BarrierKU.domain.indoor.Significant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Getter
@AllArgsConstructor
public class BuildingResponse {

    @Schema(description = "건물 아이디", example = "1")
    private Long id;
    @Schema(description = "건물 번호", example = "2")
    private Integer number;
    @Schema(description = "건물명", example = "경영관")
    private String name;
    @Schema(description = "건물 담당 부서", example = "경영(전문)대학원, 경영대학")
    private String department;
    @Schema(description = "건물 이미지", example = "image.png")
    private String image;
    @Schema(description = "해당 건물의 강의 여부", example = "true")
    private boolean lecture;
    @Schema(description = "문 정보 리스트")
    private List<DoorInfo> doorInfos;
    @Schema(description = "해당 건물에 존재하는 편의시설 종류", example = "[\"은행\", \"휴게실\", \"카페\"]")
    private Set<String> facilityPurposes;
    @Schema(description = "특이사항 리스트")
    private List<SignificantInfo> significantInfos;
    @Schema(description = "층별 공간 정보")
    private List<FloorResponse> floorList;
    @Schema(description = "위도", example = "37.54321")
    private double latitude;
    @Schema(description = "경도", example = "127.11111")
    private double longitude;

    @Getter
    @AllArgsConstructor
    public static class SignificantInfo {
        @Schema(description = "특이사항 아이디", example = "1")
        private Long id;
        @Schema(description = "설명", example = "2층 구름다리 통로로 진입 가능")
        private String description;
        @Schema(description = "특이사항 사진 리스트", example = "[\"image.png\", \"image2.png\"]")
        private List<String> imageUrl;

        private SignificantInfo(Significant significant) {
            id = significant.getId();
            description = significant.getDescription();
            imageUrl = (significant.getImages() != null ? significant.getImages() : Collections
                    .<SignificantImage>emptyList()).stream().map(SignificantImage::getUrl).toList();
        }
    }

    public BuildingResponse(Long buildingId, int number, String name, String department, String image, boolean lecture, double latitude, double longitude, Set<String> purposes, List<DoorInfo> doorInfos, List<Significant> significants, List<FloorResponse> floorList) {
        this.id = buildingId;
        this.number = number;
        this.name = name;
        this.department = department;
        this.image = image;
        this.lecture = lecture;
        this.latitude = latitude;
        this.longitude = longitude;
        this.facilityPurposes = purposes;
        this.doorInfos = doorInfos;
        this.significantInfos = significants.stream().map(significant -> new SignificantInfo(significant)).toList();
        this.floorList = floorList;
    }
}
