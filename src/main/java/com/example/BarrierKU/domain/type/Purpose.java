package com.example.BarrierKU.domain.type;

import lombok.Getter;

@Getter
public enum Purpose {
    BANK("은행"),
    POSTOFFICE("우체국"),
    CULTUER("문화시설"),
    COPY("복사실"),
    STORE("복지매장"),
    RESTAURANT("식당"),
    CAFE("카페"),
    CONVINIENCE("편의점"),
    KHUB("K-Hub"),
    KCUBE("K-Cube"),
    FOYER("휴게실"),
    PARKINGLOT("주차장"),
    ELEVATOR("엘리베이터"),
    TOILET("장애인화장실");

    private final String value;

    Purpose(String value) {
        this.value = value;
    }
}
