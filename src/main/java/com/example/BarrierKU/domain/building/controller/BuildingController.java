package com.example.BarrierKU.domain.building.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.building.dto.SpaceResponse;
import com.example.BarrierKU.domain.building.dto.BuildingResponse;
import com.example.BarrierKU.domain.building.service.BuildingService;
import com.example.BarrierKU.domain.building.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.GET_BUILDING;
import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.GET_SPACE_INFO;

@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;
    private final SpaceService spaceService;

    @GetMapping("/{buildingId}")
    @CustomExceptionDescription(GET_BUILDING)
    public BaseResponse<BuildingResponse> getBuilding(@PathVariable Long buildingId){
        BuildingResponse response = buildingService.findBuildingById(buildingId);
        return BaseResponse.ok(response);
    }

    @GetMapping("{buildingId}/spaces/{spaceId}")
    @CustomExceptionDescription(GET_SPACE_INFO)
    public BaseResponse<SpaceResponse> getSpaceInfo (
            @PathVariable Long buildingId,
            @PathVariable Long spaceId,
            @RequestParam int type
    ) {
        SpaceResponse response = spaceService.getSpaceInfo(buildingId, spaceId, type);
        return BaseResponse.ok(response);
    }

}
