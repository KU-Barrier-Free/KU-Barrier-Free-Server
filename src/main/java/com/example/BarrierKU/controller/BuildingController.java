package com.example.BarrierKU.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.dto.response.BuildingResponse;
import com.example.BarrierKU.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.GET_BUILDING;

@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/{buildingId}")
    @CustomExceptionDescription(GET_BUILDING)
    public BaseResponse<BuildingResponse> getBuilding(@PathVariable Long buildingId){
        BuildingResponse response = buildingService.findBuildingById(buildingId);
        return BaseResponse.ok(response);
    }


}
