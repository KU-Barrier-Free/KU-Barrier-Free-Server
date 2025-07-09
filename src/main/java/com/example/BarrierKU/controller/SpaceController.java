package com.example.BarrierKU.controller;

import com.example.BarrierKU.dto.ApiResponse;
import com.example.BarrierKU.dto.SpaceResponse;
import com.example.BarrierKU.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buildings/{buildingId}")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @GetMapping("/spaces/{spaceId}")
    public ResponseEntity<ApiResponse<SpaceResponse>> getSpaceDetail(
            @PathVariable Long buildingId,
            @PathVariable Long spaceId,
            @RequestParam int type
    ) {
        try {
            SpaceResponse response = spaceService.getSpaceInfo(spaceId, type);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.fail(50000, "서버 내부 오류가 발생했습니다"));
        }
    }
}
