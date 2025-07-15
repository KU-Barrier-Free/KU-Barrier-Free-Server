package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.image.RoomImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomImageResponse {
    @Schema(description = "공간 이미지 URL", example = "https://example.com/image1.png")
    private String imageUrl;
    @Schema(description = "공간 이미지 종류", example = "DOOR")
    private String imageType;

    public static RoomImageResponse from(RoomImage roomImage) {
        return new RoomImageResponse(
                roomImage.getUrl(),
                roomImage.getImageType().name()
        );
    }
}

