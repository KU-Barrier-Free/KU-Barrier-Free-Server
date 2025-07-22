package com.example.BarrierKU.domain.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Set;

public record FloorResponse(
        @Schema(description = "도면 이미지", example = "image.png")
        List<String> drawings,
        @Schema(description = "해당 층에 존재하는 편의시설 종류", example = "[\"은행\", \"휴게실\", \"카페\"]")
        Set<String> purposes,
        @Schema(description = "공간의 요약된 정보",
        example = "[{" +
                "\"id\": 1," +
                "\"roomNumber\": \"104-1호\"," +
                "\"roomName\": \"강의실\"," +
                "\"comment\": \"원형책상\"," +
                "\"roomImages\": [\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]," +
                "\"isLecture\": true" +
                "}]")
        List<SpaceSummary> spaceSummaries
) {
}
