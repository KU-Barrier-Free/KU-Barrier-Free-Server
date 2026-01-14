package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GateResponse(
        @Schema(description = "출입문 이미지", example = "http://image1.png")
        String imageUrl,
        @Schema(description = "출입문 설명", example = "건국대학교 후문으로 어린이대공원역과 가장 가깝다")
        String description,
        @Schema(description = "위도", example = "37.1234")
        double latitude,
        @Schema(description = "경도", example = "127.1234")
        double longitude
) {
    public static GateResponse of(
            String imageUrl,
            String description,
            double latitude,
            double longitude
    ) {
        return new GateResponse(
                imageUrl,
                description,
                latitude,
                longitude
        );
    }
}
