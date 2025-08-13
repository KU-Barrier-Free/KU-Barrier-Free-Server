package com.example.BarrierKU.domain.facility.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.facility.dto.FacilitiesResponse;
import com.example.BarrierKU.domain.facility.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.*;

@Slf4j
@Tag(name = "Facility", description = "Facility API")
@RequiredArgsConstructor
@RequestMapping("/facilities")
@RestController
public class FacilityController {
    private final FacilityService facilityService;

    @Operation(
            summary = "편의시설 조회 API",
            description = "편의시설 및 편의시설이 속한 건물 정보를 보여주기 위한 API 입니다."
    )
    @GetMapping("/{facilityId}")
    @CustomExceptionDescription(GET_FACILITY)
    public BaseResponse<FacilitiesResponse> getFacility(@PathVariable Long facilityId) {
        log.debug("[getFacility] 편의시설 조회, id = {}", facilityId);
        FacilitiesResponse response = facilityService.getFacility(facilityId);
        return BaseResponse.ok(response);
    }
}
