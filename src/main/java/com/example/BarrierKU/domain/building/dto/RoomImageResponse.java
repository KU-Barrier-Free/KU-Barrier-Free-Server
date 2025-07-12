package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.image.RoomImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomImageResponse {
    private String imageUrl;
    private String imageType;

    public static RoomImageResponse from(RoomImage roomImage) {
        return new RoomImageResponse(
                roomImage.getUrl(),
                roomImage.getImageType().name()
        );
    }
}

