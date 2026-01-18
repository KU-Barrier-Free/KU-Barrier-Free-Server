package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HomeResponse(
        @Schema(description = "건물들 정보")
        List<HomeBuildingItem> buildings,
        @Schema(description = "특이사항들 정보")
        List<HomeEtcItem> significants,
        @Schema(description = "연석들 정보")
        List<HomeEtcItem> curbs,
        @Schema(description = "경사로들 정보")
        List<HomeEtcItem> ramps,
        @Schema(description = "계단들 정보")
        List<HomeEtcItem> stairs,
        @Schema(description = "출입문 정보")
        List<HomeGateItem> gates
) {
    public static HomeResponse of(List<HomeBuildingItem> buildings,
                                  List<HomeEtcItem> significants,
                                  List<HomeEtcItem> curbs,
                                  List<HomeEtcItem> ramps,
                                  List<HomeEtcItem> stairs,
                                  List<HomeGateItem> gates) {
        return new HomeResponse(
                buildings,
                significants,
                curbs,
                ramps,
                stairs,
                gates
        );
    }
}

