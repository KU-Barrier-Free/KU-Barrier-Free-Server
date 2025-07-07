package com.example.BarrierKU.controller;

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
    public ResponseEntity<SpaceResponse> getSpaceDetail(
            @PathVariable Long buildingId,
            @PathVariable Long spaceId,
            @RequestParam int type
    ) {
        SpaceResponse response = spaceService.getResponse(spaceId, type);
        return ResponseEntity.ok(response);
    }
}
