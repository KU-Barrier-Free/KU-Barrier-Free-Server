package com.example.BarrierKU.domain.path.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.path.dto.PathRecommendationsResponse;
import com.example.BarrierKU.domain.path.service.PathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.FIND_PATH;

@Tag(name = "Path", description = "Path API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/path")
public class PathController {
    private final PathService pathService;

    // 경로 추천 - GeoJson
    @Operation(
            summary = "경로 추천 API",
            description = "특정 출발 건물로부터 도착 건물까지의 경로를 찾아 반환해주는 API 입니다. 최단 경로 / 계단 없는 경로 / 배리어프리 경로를 반환합니다."
    )
    @GetMapping
    @CustomExceptionDescription(FIND_PATH)
    public BaseResponse<PathRecommendationsResponse> findPath(
            @RequestParam Long srcId,
            @RequestParam String srcType,
            @RequestParam Long destId,
            @RequestParam String destType
    ) {
        PathRecommendationsResponse response = pathService.findAllPaths(srcId, srcType, destId, destType);
        return BaseResponse.ok(response);
    }
}
