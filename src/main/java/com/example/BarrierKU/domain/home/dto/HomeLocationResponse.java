package com.example.BarrierKU.domain.home.dto;

import java.math.BigDecimal;

public record HomeLocationResponse(
        Long id,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
