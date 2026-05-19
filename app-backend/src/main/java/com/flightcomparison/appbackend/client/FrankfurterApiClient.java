package com.flightcomparison.appbackend.client;

import com.flightcomparison.appbackend.exception.ApiCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FrankfurterApiClient {

    private static final Logger log = LoggerFactory.getLogger(FrankfurterApiClient.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final RestClient restClient;

    public FrankfurterApiClient(@Qualifier("frankfurterRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency, LocalDate date) {
        try {
            // Building URL path
            String path = (date == null) ? "/latest" :  "/" + DATE_FORMATTER.format(date);
            String url = path + "?from=" + fromCurrency + "&to=" + toCurrency;

            // Execute the HTTP GET request
            FrankfurterRateResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(FrankfurterRateResponse.class);

            // Validating Response
            if (response == null || response.rate() == null) {
                throw new ApiCallException("Empty or invalid response from the Frankfurter API");
            }
            return response.rate();
        } catch (RestClientException e) {
            log.error("Failed to fetch exchange rate from Frankfurter API: {} -> {} on {}",
                fromCurrency, toCurrency, date, e);
            throw new ApiCallException("Failed to fetch exchange rate from currency service", e);
        }
    }
}
