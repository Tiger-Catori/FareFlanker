package com.flightcomparison.appbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@EqualsAndHashCode(of = "iataCode")
public class AirportSuggestionDTO {
    @NotBlank
    @Size(min = 3, max = 3)
    private final String iataCode;

    @NotBlank
    private final String airportName;

    @NotBlank
    private final String city;

    @NotBlank
    private final String country;

    public String getDisplayText() {
        return String.format("%s - %s", iataCode, airportName);
    }
}