package com.example.BarrierKU.domain.path.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "GeoJSON Feature")
public record GeoJsonFeature(

        @Schema(description = "항상 'Feature'로 고정")
        String type,

        @Schema(description = "지오메트리 정보")
        Geometry geometry,

        @Schema(description = "Feature의 속성 정보 (예: 타입, 인덱스 등)")
        Map<String, Object> properties
) {
    @Schema(description = "GeoJSON Geometry")
    public record Geometry(
            @Schema(description = "지오메트리 타입 (예: Point, LineString 등)")
            String type,

            @Schema(description = "좌표 정보. Point는 [경도, 위도], LineString은 [[경도, 위도], [경도, 위도]] 형태")
            Object coordinates
    ) {}
}