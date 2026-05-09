package com.flightcomparison.appbackend.model.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flightcomparison.appbackend.model.enums.CabinClass;
import com.flightcomparison.appbackend.model.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightResultDTO {

    private final String airlineName;
    private final String flightNumber;
    private final String originIata;
    private final String originAirportName;
    private final String originCity;
    private final String destinationIata;
    private final String destinationAirportName;
    private final String destinationCity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private final ZonedDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private final ZonedDateTime arrivalTime;

    @PositiveOrZero
    private final int durationMinutes;

    @PositiveOrZero
    private final int stops;

    @NotNull
    @PositiveOrZero
    private final BigDecimal price;

    @NotNull
    private final CurrencyType currency;

    @NotNull
    private final CabinClass cabinClass;
}