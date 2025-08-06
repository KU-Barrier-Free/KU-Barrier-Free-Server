package com.example.BarrierKU.domain.home.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.home.dto.BuildingInfoResponse;
import com.example.BarrierKU.domain.home.dto.HomeResponse;
import com.example.BarrierKU.domain.home.dto.OutsideSignificantResponse;
import com.example.BarrierKU.domain.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(
            summary = "교외 특이사항 조회 API",
            description = "교외 특이사항의 정보를 보여주는 API 입니다."
    )
    @GetMapping("/outside-significants/{outsideSignificantId}")
    @CustomExceptionDescription(GET_OUTSIDE_SIGNIFICANT_INFO)
    public BaseResponse<OutsideSignificantResponse> getOutsideSignificantInfo(@PathVariable("outsideSignificantId") Long outsideSignificantId) {
        return BaseResponse.ok(homeService.getOutsideSignificantInfo(outsideSignificantId));
    }

    @Operation(
            summary = "건물 요약 API",
            description = "홈 화면에서 건물을 터치할 시 나오는 바텀시트 + 문 좌표 입니다."
    )
    @GetMapping("/{buildingId}")
    @CustomExceptionDescription(GET_BUILDING)
    public BaseResponse<BuildingInfoResponse> getBuildingInfo(@PathVariable("buildingId") Long buildingId) {
        return BaseResponse.ok(homeService.getBuildingInfo(buildingId));
    }
}
