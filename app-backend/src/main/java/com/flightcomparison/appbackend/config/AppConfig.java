package com.flightcomparison.appbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${frankfurter.api.base-url}")
    private String frankfurterBaseUrl;

    @Bean
    public RestClient frankfurterRestClient() {
        // Creating a request factory with timeouts
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());

        return RestClient.builder()
                .baseUrl(frankfurterBaseUrl)
                .requestFactory(factory)
                .build();
    }
}