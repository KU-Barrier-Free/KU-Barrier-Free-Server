package com.example.BarrierKU.domain.door.dto;

import com.example.BarrierKU.domain.image.DoorImage;
import com.example.BarrierKU.domain.indoor.Door;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DoorInfo {
    private Long id;
    private boolean wheelchair;
    private List<String> imageUrl;
    private double latitude;
    private double longitude;
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
