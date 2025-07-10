package com.example.BarrierKU.domain.home.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.common.swagger.SwaggerResponseDescription;
import com.example.BarrierKU.domain.home.dto.HomeResponse;
import com.example.BarrierKU.domain.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.*;

@Tag(name = "Home", description = "Home API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(
            summary = "홈 화면 API",
            description = "홈 화면에 건물, 특이사항, 연석, 경사로, 계단 정보를 보여주기 위한 API 입니다."
    )
    @GetMapping
    @CustomExceptionDescription(DEFAULT)
    public BaseResponse<HomeResponse> getHomeInfo() {
        return BaseResponse.ok(homeService.getHomeInfo());
    }
}
