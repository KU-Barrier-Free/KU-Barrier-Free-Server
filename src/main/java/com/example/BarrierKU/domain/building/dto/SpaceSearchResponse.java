package com.example.BarrierKU.domain.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SpaceSearchResponse {
    @Schema(description = "검색 결과 수", example = "1")
    private int count;
    @Schema(description = "강의실 요약 정보")
    private List<SpaceSummary> spaces;
}
