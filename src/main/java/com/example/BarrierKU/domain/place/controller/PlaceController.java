package com.example.BarrierKU.domain.place.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.place.dto.PlaceSearchResponse;
import com.example.BarrierKU.domain.place.service.PlaceService;
import com.example.BarrierKU.domain.search.dto.PopularKeywordResponse;
import com.example.BarrierKU.domain.search.service.SearchKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "Place", description = "장소 검색 API")
public class PlaceController {
    private final PlaceService placeService;
    private final SearchKeywordService searchKeywordService;

    @Operation(
            summary = "장소 검색 API",
            description = "건물명 또는 편의시설명을 기반으로 장소를 검색하는 API입니다.",
            parameters = {
                    @Parameter(name = "keyword", description = "검색할 키워드 (건물명 또는 편의시설명)", required = true)
            }
    )
    @GetMapping("/places/search")
    public BaseResponse<PlaceSearchResponse> searchPlaces(@RequestParam("keyword") String keyword) {
        log.debug("[searchPlaces] keyword = {}", keyword);
        if (keyword == null || keyword.isEmpty()) {
            return BaseResponse.ok(new PlaceSearchResponse());
        }
        searchKeywordService.recordSearch(keyword);

        PlaceSearchResponse response = placeService.getPlaces(keyword);
        return BaseResponse.ok(response);
    }

    @GetMapping("/place/popular-keywords")
    public BaseResponse<PopularKeywordResponse> getPopularKeywords() {
        List<String> keywords = searchKeywordService.getTopKeywords(4);
        PopularKeywordResponse response = new PopularKeywordResponse(keywords);

        return BaseResponse.ok(response);
    }
}
