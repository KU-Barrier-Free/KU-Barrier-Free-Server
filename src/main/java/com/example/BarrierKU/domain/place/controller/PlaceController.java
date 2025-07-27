package com.example.BarrierKU.domain.place.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.place.dto.PlaceSearchResponse;
import com.example.BarrierKU.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/places/search")
    public BaseResponse<PlaceSearchResponse<?>> searchPlaces(@RequestParam("keyword") String keyword,
                                                          @RequestParam(required = false) double latitude,
                                                          @RequestParam(required = false) double longitude
                                                           ) {
        if (keyword == null || keyword.isEmpty()) {
            return BaseResponse.ok(PlaceSearchResponse.ofFacility(new ArrayList<>()));
        }

        PlaceSearchResponse<?> response = placeService.getPlaces(keyword, latitude, longitude);
        return BaseResponse.ok(response);
    }
}
