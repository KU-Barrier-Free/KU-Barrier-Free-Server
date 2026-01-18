package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record HomeGateItem(
        @Schema(description = "Gate id", example = "1")
        Long id,
        @Schema(description = "출입문 이름", example = "건국문")
        String name,
        @Schema(description = "위도", example = "37.1234")
        double latitude,
        @Schema(description = "경도", example = "127.1234")
        double longitude
) {
    public static HomeGateItem of(Long id, String name, double latitude, double longitude) {
        return new HomeGateItem(
                id,
                name,
                latitude,
                longitude
        );
    }
}
