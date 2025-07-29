package com.example.BarrierKU.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record OutsideSignificantResponse(
        @Schema(description = "교외 특이사항 이미지", example = "[\"http://image1.png\", \"http://image2.png\"]")
        List<String> imageUrls,
        @Schema(description = "교외 특이사항", example = "사진 기준 왼쪽에 경사로가 있어서 장애 학우들도 이용 가능합니다.")
        String description,
        @Schema(description = "위도", example = "37.123123")
        double latitude,
        @Schema(description = "경도", example = "127.123123")
        double longitude
) {
        public static OutsideSignificantResponse of(List<String> imageUrls, String description, double latitude, double longitude) {
                return new OutsideSignificantResponse(imageUrls, description, latitude, longitude);
        }
}
