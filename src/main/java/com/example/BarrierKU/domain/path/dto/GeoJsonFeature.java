package com.example.BarrierKU.domain.path.dto;

import java.util.Map;

public record GeoJsonFeature(
        String type,
        Geometry geometry,
        Map<String, Object> properties // 새로 추가
) {
    public record Geometry(
            String type,
            Object coordinates
    ) {}
}
