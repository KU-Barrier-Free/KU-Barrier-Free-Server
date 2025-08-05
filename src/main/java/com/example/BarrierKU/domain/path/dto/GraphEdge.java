package com.example.BarrierKU.domain.path.dto;

public record GraphEdge(
        String from,
        String to,
        double weight,
        double distance
) {
}
