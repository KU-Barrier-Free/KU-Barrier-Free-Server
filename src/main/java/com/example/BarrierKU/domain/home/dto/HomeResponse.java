package com.example.BarrierKU.domain.home.dto;

import java.util.List;

public record HomeResponse(
        List<HomeLocationResponse> buildings,
        List<HomeLocationResponse> significants,
        List<HomeLocationResponse> curbs,
        List<HomeLocationResponse> ramps,
        List<HomeLocationResponse> stairs
) {
    public static HomeResponse of(List<HomeLocationResponse> buildings,
                                  List<HomeLocationResponse> significants,
                                  List<HomeLocationResponse> curbs,
                                  List<HomeLocationResponse> ramps,
                                  List<HomeLocationResponse> stairs) {
        return new HomeResponse(
                buildings,
                significants,
                curbs,
                ramps,
                stairs
        );
    }
}

