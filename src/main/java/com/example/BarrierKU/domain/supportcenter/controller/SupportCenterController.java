package com.example.BarrierKU.domain.supportcenter.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.supportcenter.dto.NoticeResponse;
import com.example.BarrierKU.domain.supportcenter.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.DEFAULT;
import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.GET_NOTICE;

@Tag(name = "Support Center", description = "Support Center API")
@RestController
@RequestMapping("/support-center")
@RequiredArgsConstructor
public class SupportCenterController {

    private final NoticeService noticeService;

    @Operation(
            summary = "공지사항 크롤링 API",
            description = "장애학생지원센터의 최근 공지사항을 보여주기 위한 API 입니다."
    )
    @GetMapping
    @CustomExceptionDescription(GET_NOTICE)
    public BaseResponse<List<NoticeResponse>> getNotices() {
        List<NoticeResponse> response = noticeService.getNotices();
        return BaseResponse.ok(response);
    }
}
