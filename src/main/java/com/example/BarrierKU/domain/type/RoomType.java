package com.example.BarrierKU.domain.type;

import lombok.Getter;

@Getter
public enum RoomType {
    FLAT("평탄식"),
    STEPPED("계단식");

    private String value;

    RoomType(String value) {
        this.value = value;
    }
}
