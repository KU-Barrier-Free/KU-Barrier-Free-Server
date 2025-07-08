package com.example.BarrierKU.domain.facility.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.facility.dto.FacilitiesResponse;
import com.example.BarrierKU.domain.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/facilities")
@RestController
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping("/{facilityId}")
    public BaseResponse<FacilitiesResponse> getFacility(@PathVariable Long facilityId) {
        log.debug("[getFacility] 편의시설 조회, id = {}", facilityId);
        FacilitiesResponse response = facilityService.getFacility(facilityId);
        return new BaseResponse<>(response);
    }
}
