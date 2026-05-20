package com.flightcomparison.appbackend.service;

import com.flightcomparison.appbackend.client.FrankfurterApiClient;
import com.flightcomparison.appbackend.exception.ApiCallException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final FrankfurterApiClient frankfurterApiClient;

    public CurrencyConversionServiceImpl(FrankfurterApiClient frankfurterApiClient) {
        this.frankfurterApiClient = frankfurterApiClient;
    }

    @Override
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        // 1. Input validation
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (fromCurrency == null || fromCurrency.isBlank()) {
            throw new IllegalArgumentException("From currency code cannot be null or blank");
        }
        if (toCurrency == null || toCurrency.isBlank()) {
            throw new IllegalArgumentException("To currency code cannot be null or blank");
        }

        // 2. Same currency – no conversion needed
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }

        // 3. Get exchange rate (cached, may throw ApiCallException)
        BigDecimal rate = frankfurterApiClient.getExchangeRate(fromCurrency, toCurrency, null);

        // 4. Multiply and round to 2 decimal places (money standard)
        BigDecimal converted = amount.multiply(rate);
        converted = converted.setScale(2, RoundingMode.HALF_UP);

        return converted;
    }

    @Override
    @Cacheable(value = "exchangeRates", key = "#fromCurrency + '-' + #toCurrency")
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        // Delegate to the client, using null for date to get the latest rate
        return frankfurterApiClient.getExchangeRate(fromCurrency, toCurrency, null);
    }

    @Override
    @Cacheable(value = "supportedCurrencies", unless = "#result == null or #result.isEmpty()")
    public Set<String> getSupportedCurrencies() {
        return frankfurterApiClient.getSupportedCurrencies();
    }
}