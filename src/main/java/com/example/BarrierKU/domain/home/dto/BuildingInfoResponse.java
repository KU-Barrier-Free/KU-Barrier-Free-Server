package com.example.BarrierKU.domain.home.dto;

import com.example.BarrierKU.domain.door.dto.DoorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class BuildingInfoResponse {
    @Schema(description = "건물 아이디", example = "1")
    private Long id;
    @Schema(description = "건물 번호", example = "2")
    private int number;
    @Schema(description = "건물명", example = "경영관")
    private String name;
    @Schema(description = "건물 강의 여부", example = "true")
    private boolean lecture;
    @Schema(description = "문 정보 리스트",
            example = "[{" +
                    "\"id\": 1," +
                    "\"wheelchair\": true," +
                    "\"imageUrl\": [\"https://example.com/door1.jpg\", \"https://example.com/door2.jpg\"]," +
                    "\"latitude\": 37.12345," +
                    "\"longitude\": 127.98765," +
                    "\"label\": \"A\"" +
                    "}," +
                    "{" +
                    "\"id\": 2," +
                    "\"wheelchair\": false," +
                    "\"imageUrl\": [\"https://example.com/door3.jpg\"]," +
                    "\"latitude\": 37.54321," +
                    "\"longitude\": 127.11111," +
                    "\"label\": \"B\"" +
                    "}]"
    )
    private List<DoorInfo> doorInfos;
    @Schema(description = "해당 건물에 존재하는 편의시설 종류", example = "[\"은행\", \"휴게실\", \"카페\"]")
    private Set<String> facilityPurposes;
    @Schema(description = "위도", example = "37.54321")
    private double latitude;
    @Schema(description = "경도", example = "127.11111")
    private double longitude;
}
