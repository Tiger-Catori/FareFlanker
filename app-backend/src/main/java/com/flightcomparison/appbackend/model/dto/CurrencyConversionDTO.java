package com.flightcomparison.appbackend.model.dto;

import com.flightcomparison.appbackend.model.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionDTO {

    @NotNull(message = "Original currency is required")
    private CurrencyType fromCurrency;

    @NotNull(message = "Target currency is required")
    private CurrencyType toCurrency;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private BigDecimal convertedAmount;   // populated in response

    private BigDecimal exchangeRate;      // populated in response

    private LocalDate rateDate;           // date of the exchange rate (response only)
}