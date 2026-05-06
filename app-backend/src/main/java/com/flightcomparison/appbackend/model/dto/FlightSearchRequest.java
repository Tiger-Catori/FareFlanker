package com.flightcomparison.appbackend.model.dto;

import com.flightcomparison.appbackend.model.enums.TripType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class FlightSearchRequest {
    /**
     * Data Transfer Object:
     * Carries the user's search input form the front end to the backend.
     * Used by FlightSearchController & Validated before reaching the service.
     * */
    @NotBlank(message = "Origin cannot be blank.")
    @Size(min = 3, max = 3)
    private String originCode;

    @NotBlank(message = "Destination cannot be blank.")
    @Size(min = 3, max = 3)
    private String destinationCode;

    @NotNull(message = "Departure date is required.")
    @FutureOrPresent(message = "Departure date cannot be in the past.")
    private LocalDate departureDate;

    @FutureOrPresent(message = "Return date cannot be in the past.")
    private LocalDate returnDate; // optional for one-way trips

    public TripType getTripType() {
        if (returnDate == null) return TripType.ONE_WAY;
        else return TripType.ROUND_TRIP;
    }

    @AssertTrue(message = "Return date must be after the departure date.")
    public boolean isReturnDateValid() {
        if (returnDate == null) return true; // Allow for one-way trip
        return departureDate != null && returnDate.isAfter(departureDate);
    }

    @AssertTrue(message = "Origin & destination cannot be the same.")
    public boolean isDifferentAirports() {
        return !originCode.equalsIgnoreCase(destinationCode);
    }


}
