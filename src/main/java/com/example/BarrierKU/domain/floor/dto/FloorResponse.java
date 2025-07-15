package com.example.BarrierKU.domain.floor.dto;

import com.example.BarrierKU.domain.type.Purpose;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Set;

public record FloorResponse(
        @Schema(description = "도면 이미지")
        String floorPlan,
        @Schema(description = "해당 층에 존재하는 편의시설 종류")
        Set<String> purposes,
        @Schema(description = "공간의 요약된 정보")
        List<SpaceSummary> spaceSummaries
) {
}
