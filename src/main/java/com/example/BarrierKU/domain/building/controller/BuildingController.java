package com.example.BarrierKU.domain.building.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.building.dto.FloorResponse;
import com.example.BarrierKU.domain.building.dto.SpaceResponse;
import com.example.BarrierKU.domain.building.dto.BuildingResponse;
import com.example.BarrierKU.domain.building.dto.SpaceSearchResponse;
import com.example.BarrierKU.domain.building.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.*;

@Tag(name = "Building", description = "Building API")
@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/{buildingId}")
    @CustomExceptionDescription(GET_BUILDING)
    public BaseResponse<BuildingResponse> getBuilding(@PathVariable Long buildingId) {
        BuildingResponse response = buildingService.findBuildingById(buildingId);
        return BaseResponse.ok(response);
    }

    @Operation(
            summary = "공간 정보 조회 API",
            description = "강의실의 책상 및 의자 형태, 사진 등의 정보를 보여주기 위한 API 입니다."
    )
    @GetMapping("{buildingId}/spaces/{spaceId}")
    @CustomExceptionDescription(GET_SPACE_INFO)
    public BaseResponse<SpaceResponse> getSpaceInfo(
            @Parameter(description = "건물 ID", example = "1")
            @PathVariable Long buildingId,
            @Parameter(description = "공간 ID", example = "1")
            @PathVariable Long spaceId,
            @Parameter(description = "수업 여부", example = "1")
            @RequestParam int type
    ) {
        SpaceResponse response = buildingService.getSpaceInfo(buildingId, spaceId, type);
        return BaseResponse.ok(response);
    }

    @Operation(
            summary = "건물 내 공간 검색 API",
            description = "강의실 검색을 위한 API 입니다."
    )
    @GetMapping("{buildingId}/spaces/search")
    @CustomExceptionDescription(DEFAULT)
    public BaseResponse<SpaceSearchResponse> searchSpace(
            @Parameter(description = "건물 ID", example = "1")
            @PathVariable Long buildingId,
            @Parameter(description = "검색어", example = "전산실습실")
            @RequestParam String keyword
    ) {
        SpaceSearchResponse response = buildingService.searchSpace(buildingId, keyword);
        return BaseResponse.ok(response);
    }

}
