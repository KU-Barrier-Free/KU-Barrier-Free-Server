package com.example.BarrierKU.domain.door.dto;

import com.example.BarrierKU.domain.image.DoorImage;
import com.example.BarrierKU.domain.indoor.Door;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DoorInfo {
    @Schema(description = "문 id", example = "1")
    private Long id;
    @Schema(description = "휠체어 입장 가능 여부", example = "false")
    private boolean wheelchair;
    @Schema(description = "문 사진 리스트", example = "[\"image.png\", \"image2.png\"]")
    private List<String> imageUrl;
    @Schema(description = "위도", example = "32.54321")
    private double latitude;
    @Schema(description = "경도", example = "137.54321")
    private double longitude;
    @Schema(description = "문 라벨", example = "D")
    private String label;

    public DoorInfo(Door door) {
        id = door.getId();
        wheelchair = door.isWheelchair();
        imageUrl = door.getImages().stream().map(DoorImage::getUrl).toList();
        latitude = door.getSpot().getY();
        longitude = door.getSpot().getX();
        label = door.getLabel();
    }
}
