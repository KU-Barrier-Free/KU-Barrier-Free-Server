package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HomeResponse(
        @Schema(description = "건물들 정보")
        List<HomeLocationResponse> buildings,
        @Schema(description = "특이사항들 정보")
        List<HomeLocationResponse> significants,
        @Schema(description = "연석들 정보")
        List<HomeLocationResponse> curbs,
        @Schema(description = "경사로들 정보")
        List<HomeLocationResponse> ramps,
        @Schema(description = "계단들 정보")
        List<HomeLocationResponse> stairs
) {
    public static HomeResponse of(List<HomeLocationResponse> buildings,
                                  List<HomeLocationResponse> significants,
                                  List<HomeLocationResponse> curbs,
                                  List<HomeLocationResponse> ramps,
                                  List<HomeLocationResponse> stairs) {
        return new HomeResponse(
                buildings,
                significants,
                curbs,
                ramps,
                stairs
        );
    }
}

