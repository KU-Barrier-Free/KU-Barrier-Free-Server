package com.example.BarrierKU.domain.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SpaceSummary(
        @Schema(description = "공간 아이디")
        Long id,
        @Schema(description = "호수")
        String roomNumber,
        @Schema(description = "호실명")
        String roomName,
        @Schema(description = "특이사항")
        String comment,
        @Schema(description = "방 이미지 url")
        List<String> roomImages,
        @Schema(description = "강의여부")
        boolean isLecture
) {
}
