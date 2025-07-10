package com.example.BarrierKU.domain.home.dto;

public record HomeLocationResponse(
        Long id,
        double latitude,
        double longitude
) {
    public static HomeLocationResponse of(Long id, double latitude, double longitude) {
        return new HomeLocationResponse(
                id,
                latitude,
                longitude
        );
    }

}
