package com.example.BarrierKU.domain.door.dto;

import com.example.BarrierKU.domain.Image.DoorImage;
import com.example.BarrierKU.domain.Indoor.Door;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DoorResponse {
    private Long id;

    private boolean wheelchair;

    private String significant;

    private List<DoorImage> images;

    public static DoorResponse from(Door door) {
        return new DoorResponse(door.getId(), door.isWheelchair(), door.getSignificant(), door.getImages());
    }

}
