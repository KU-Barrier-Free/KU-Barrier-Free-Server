package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record OutdoorSignificantResponse(
        @Schema(description = "교외 특이사항 이미지", example = "http://image1.png")
        String imageUrl,
        @Schema(description = "교외 특이사항", example = "사진 기준 왼쪽에 경사로가 있어서 장애 학우들도 이용 가능합니다.")
        String description,
        @Schema(description = "위도", example = "37.123123")
        double latitude,
        @Schema(description = "경도", example = "127.123123")
        double longitude
) {
}
