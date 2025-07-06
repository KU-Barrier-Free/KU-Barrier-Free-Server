package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Image.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponse {
//    private Long imageId;
    private String imageUrl;
    private String imageType;

    public static ImageResponse from(Image image) {
        return ImageResponse.builder()
//                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .imageType(image.getImageType().name())
                .build();
    }
}

