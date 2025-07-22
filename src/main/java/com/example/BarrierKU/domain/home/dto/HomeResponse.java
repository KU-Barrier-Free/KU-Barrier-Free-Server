package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HomeResponse(
        @Schema(description = "건물들 정보")
        List<HomeItem> buildings,
        @Schema(description = "특이사항들 정보")
        List<HomeItem> significants,
        @Schema(description = "연석들 정보")
        List<HomeItem> curbs,
        @Schema(description = "경사로들 정보")
        List<HomeItem> ramps,
        @Schema(description = "계단들 정보")
        List<HomeItem> stairs
) {
    public static HomeResponse of(List<HomeItem> buildings,
                                  List<HomeItem> significants,
                                  List<HomeItem> curbs,
                                  List<HomeItem> ramps,
                                  List<HomeItem> stairs) {
        return new HomeResponse(
                buildings,
                significants,
                curbs,
                ramps,
                stairs
        );
    }
}

