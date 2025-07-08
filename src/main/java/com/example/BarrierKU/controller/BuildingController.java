package com.example.BarrierKU.controller;

import com.example.BarrierKU.domain.Indoor.Building;
import com.example.BarrierKU.common.ApiResponse;
import com.example.BarrierKU.dto.response.BuildingResponse;
import com.example.BarrierKU.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/{buildingId}")
    public ApiResponse<BuildingResponse> getBuilding(@PathVariable Long buildingId){
        Building building = buildingService.findBuildingById(buildingId);
        return ApiResponse.ok(BuildingResponse.from(building));
    }


}
