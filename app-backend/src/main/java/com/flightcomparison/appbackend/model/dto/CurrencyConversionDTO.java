package com.flightcomparison.appbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flightcomparison.appbackend.model.enums.CurrencyType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CurrencyConversionDTO {
    private final BigDecimal originalAmount;
    private final CurrencyType originalCurrency;
    private final BigDecimal convertedAmount;
    private final CurrencyType targetCurrency;
    private final BigDecimal exchangeRate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private final Instant timestamp;
}
