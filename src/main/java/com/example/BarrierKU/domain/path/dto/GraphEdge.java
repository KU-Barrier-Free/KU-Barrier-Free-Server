package com.example.BarrierKU.domain.path.dto;

public record GraphEdge(
        String from,
        String to,
        double fromLat,
        double fromLng,
        double toLat,
        double toLng,
        double weight,
        double distance
) {
}
