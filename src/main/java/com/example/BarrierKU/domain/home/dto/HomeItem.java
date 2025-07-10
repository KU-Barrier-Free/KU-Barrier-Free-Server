package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record HomeItem(
        @Schema(description = "각 항목들의 PK값", example = "1")
        Long id,
        @Schema(description = "위도", example = "37.123456")
        double latitude,
        @Schema(description = "경도", example = "127.123456")
        double longitude
) {
    public static HomeItem of(Long id, double latitude, double longitude) {
        return new HomeItem(
                id,
                latitude,
                longitude
        );
    }

}
