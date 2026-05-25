package com.flightcomparison.appbackend.controller;

import com.flightcomparison.appbackend.exception.ApiCallException;
import com.flightcomparison.appbackend.model.dto.CurrencyConversionDTO;
import com.flightcomparison.appbackend.service.CurrencyConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private final CurrencyConversionService currencyConversionService;

    public CurrencyController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/supported")
    public ResponseEntity<Set<String>> getSupportedCurrencies() {
        Set<String> currencies = currencyConversionService.getSupportedCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/rate")
    public ResponseEntity<BigDecimal> getExchangeRate(@RequestParam("from") String fromCurrency, @RequestParam("to") String toCurrency) {
        BigDecimal rate = currencyConversionService.getExchangeRate(fromCurrency, toCurrency);
        return ResponseEntity.ok(rate);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        boolean isApiHealthy = currencyConversionService.isApiHealthy();
        if (isApiHealthy) {
            return ResponseEntity.ok("Frankfurter currency API is reachable");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

}
