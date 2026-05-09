package com.flightcomparison.appbackend.model.dto;

import com.flightcomparison.appbackend.model.enums.CabinClass;
import com.flightcomparison.appbackend.model.enums.CurrencyType;
import com.flightcomparison.appbackend.model.enums.TripType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequest {

    @NotBlank(message = "Origin IATA code is required")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 letters")
    private String originIata;

    @NotBlank(message = "Destination IATA code is required")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 letters")
    private String destinationIata;

    @NotNull(message = "Departure date is required")
    @FutureOrPresent(message = "Departure date cannot be in the past")
    private LocalDate departureDate;

    @FutureOrPresent(message = "Return date cannot be in the past")
    private LocalDate returnDate;  // null for one‑way trips

    @NotNull(message = "Trip type is required")
    private TripType tripType;

    @NotNull(message = "Cabin class is required")
    private CabinClass cabinClass;

    @Min(value = 1, message = "At least 1 adult is required")
    @Max(value = 9, message = "Maximum 9 adults allowed")
    private Integer adults = 1;

    // Replace the String currency field with:
    @NotNull(message = "Currency code is required")
    private CurrencyType currency = CurrencyType.GBP;

    // Cross-field validation: origin and destination cannot be the same
    @AssertTrue(message = "Origin and destination cannot be the same")
    public boolean isDifferentAirports() {
        return originIata != null && destinationIata != null &&
                !originIata.equalsIgnoreCase(destinationIata);
    }

    // Cross-field validation: return date must be after departure date for round trips
    @AssertTrue(message = "Return date must be after departure date for round trips")
    public boolean isReturnDateValid() {
        if (tripType == TripType.ROUND_TRIP) {
            return returnDate != null && departureDate != null &&
                    returnDate.isAfter(departureDate);
        }
        return true; // one‑way trips have no return date requirement
    }
}