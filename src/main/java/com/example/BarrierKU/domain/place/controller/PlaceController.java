package com.example.BarrierKU.domain.place.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.place.dto.PlaceSearchResponse;
import com.example.BarrierKU.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.GET_SPACE_INFO;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "Place", description = "장소 검색 API")
public class PlaceController {
    private final PlaceService placeService;

    @Operation(
            summary = "장소 검색 API",
            description = "건물명 또는 편의시설명을 기반으로 장소를 검색하는 API입니다.",
            parameters = {
                    @Parameter(name = "keyword", description = "검색할 키워드 (건물명 또는 편의시설명)", required = true)
            }
    )
    @CustomExceptionDescription(GET_SPACE_INFO)
    @GetMapping("/places/search")
    public BaseResponse<PlaceSearchResponse<?>> searchPlaces(@RequestParam("keyword") String keyword
                                                           ) {
        log.debug("[searchPlaces] keyword = {}", keyword);
        if (keyword == null || keyword.isEmpty()) {
            return BaseResponse.ok(PlaceSearchResponse.ofFacility(new ArrayList<>()));
        }

        PlaceSearchResponse<?> response = placeService.getPlaces(keyword);
        return BaseResponse.ok(response);
    }
}
