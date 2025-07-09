package com.example.BarrierKU.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.dto.SpaceResponse;
import com.example.BarrierKU.dto.response.BuildingResponse;
import com.example.BarrierKU.service.BuildingService;
import com.example.BarrierKU.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;
    private final SpaceService spaceService;

    @GetMapping("/{buildingId}")
    public BaseResponse<BuildingResponse> getBuilding(@PathVariable Long buildingId){
        BuildingResponse response = buildingService.findBuildingById(buildingId);
        return BaseResponse.ok(response);
    }

    @GetMapping("{buildingId}/spaces/{spaceId}")
    public BaseResponse<SpaceResponse> getSpaceInfo (
            @PathVariable Long buildingId,
            @PathVariable Long spaceId,
            @RequestParam int type
    ) {
        SpaceResponse response = spaceService.getSpaceInfo(buildingId, spaceId, type);
        return BaseResponse.ok(response);
    }

}
