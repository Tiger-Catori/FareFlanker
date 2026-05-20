package com.flightcomparison.appbackend.service;

import java.math.BigDecimal;
import java.util.Set;

public interface CurrencyConversionService {
    BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency);
    BigDecimal getExchangeRate(String fromCurrency, String toCurrency);
    Set<String> getSupportedCurrencies();
}
