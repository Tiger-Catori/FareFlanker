package com.flightcomparison.appbackend.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class RoundTripFlightResultDTO {
    private final FlightResultDTO outboundLeg;
    private final FlightResultDTO inboundLeg;
    private final BigDecimal totalPrice;        // sum of outbound + inbound prices
    private final int totalDurationMinutes;     // outbound duration + inbound duration
}