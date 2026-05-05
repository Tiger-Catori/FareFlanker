package com.flightcomparison.appbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flightcomparison.appbackend.model.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightResultDTO {
    private final String airline;
    private final String flightNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private final ZonedDateTime departureTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private final ZonedDateTime arrivalTime;

    @PositiveOrZero
    private final int durationMinutes;

    @NotNull
    @PositiveOrZero
    private final BigDecimal price;

    @NotNull
    private final CurrencyType currency;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)     // omit empty list from JSON
    private final List<String> amenities;

    @PositiveOrZero
    private final int stops;

}
