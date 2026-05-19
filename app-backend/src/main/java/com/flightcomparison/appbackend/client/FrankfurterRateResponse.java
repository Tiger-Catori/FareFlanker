package com.flightcomparison.appbackend.client;

import java.math.BigDecimal;

public record FrankfurterRateResponse(
        String date,
        String base,
        String quote,
        BigDecimal rate
) {
}
