package com.example.BarrierKU.domain.floor.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SpaceSummary(
        @Schema(description = "공간 아이디")
        Long id,
        @Schema(description = "호수")
        String roomNumber,
        String roomName,
        String comment,
        List<String> roomImages,
        boolean isLecture
) {
}
