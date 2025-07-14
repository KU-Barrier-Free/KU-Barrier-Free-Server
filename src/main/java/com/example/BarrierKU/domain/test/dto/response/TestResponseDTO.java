package com.example.BarrierKU.domain.test.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TestResponseDTO(
        @Schema(description = "테스트 응답 메세지", example = "테스트 성공 응답 예시") String message) {
}
