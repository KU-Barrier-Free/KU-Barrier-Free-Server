package com.example.BarrierKU.domain.supportcenter.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record NoticeResponse (
        @Schema(description = "공지사항 제목", example = "2025년 장애대학생 취업캠프 및 채용설명회 참가자 모집")
        String title,
        @Schema(description = "날짜", example = "2025.07.30")
        String date,
        @Schema(description = "공지사항 url", example = "https://www.konkuk.ac.kr/csd/1234/subview.do?enc=Zm5j")
        String url
) {

}
