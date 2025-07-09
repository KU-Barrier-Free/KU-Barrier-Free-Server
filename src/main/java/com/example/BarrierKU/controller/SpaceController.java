package com.example.BarrierKU.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.dto.SpaceResponse;
import com.example.BarrierKU.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buildings/{buildingId}")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @GetMapping("/spaces/{spaceId}")
    public BaseResponse<SpaceResponse> getSpaceInfo (
            @PathVariable Long buildingId,
            @PathVariable Long spaceId,
            @RequestParam int type
    ) {
        SpaceResponse response = spaceService.getSpaceInfo(spaceId, type);
        return BaseResponse.ok(response);
    }
}
