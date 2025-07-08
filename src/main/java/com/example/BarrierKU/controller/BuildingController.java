package com.example.BarrierKU.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.dto.response.BuildingResponse;
import com.example.BarrierKU.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/{buildingId}")
    public BaseResponse<BuildingResponse> getBuilding(@PathVariable Long buildingId){
        BuildingResponse response = buildingService.findBuildingById(buildingId);
        return new BaseResponse(response);
    }


}
