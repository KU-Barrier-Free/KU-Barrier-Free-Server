package com.example.BarrierKU.domain.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SpaceSearchResponse {
    @Schema(description = "검색 결과 수", example = "2")
    private int count;
    private List<SpaceSummary> spaces;
}
