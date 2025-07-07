package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Image.RoomImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomImageResponse {
    private String imageUrl;
    private String imageType;

    public static RoomImageResponse from(RoomImage roomImage) {
        return RoomImageResponse.builder()
                .imageUrl(roomImage.getUrl())
                .imageType(roomImage.getImageType().name())
                .build();
    }
}

