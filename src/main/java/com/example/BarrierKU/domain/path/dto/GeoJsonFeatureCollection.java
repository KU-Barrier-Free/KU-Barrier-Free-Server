package com.example.BarrierKU.domain.path.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
        description = "GeoJSON FeatureCollection + 총 거리",
        example = """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "geometry": {
                "type": "Point",
                "coordinates": [127.07879, 37.541635]
              },
              "properties": { "type": "point", "index": 1 }
            },
            {
              "type": "Feature",
              "geometry": {
                "type": "LineString",
                "coordinates": [
                  [127.07879, 37.541635],
                  [127.07866915882109, 37.54166274319651]
                ]
              },
              "properties": { "type": "line", "index": 1 }
            },
            {
              "type": "Feature",
              "geometry": {
                "type": "Point",
                "coordinates": [127.07866915882109, 37.54166274319651]
              },
              "properties": { "type": "point", "index": 2 }
            }
          ],
          "totalDistance": "123m"
        }
        """
)
public record GeoJsonFeatureCollection(
        String type,
        List<GeoJsonFeature> features,
        String totalDistance
) {
    public GeoJsonFeatureCollection(List<GeoJsonFeature> features, String totalDistance) {
        this("FeatureCollection", features, totalDistance);
    }
}


