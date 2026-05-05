package com.flightcomparison.appbackend.model.dto;

import com.flightcomparison.appbackend.model.enums.TripType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;
@Builder
@AllArgsConstructor
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

    // private TripType tripType;

    // Getters and Setters

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public TripType getTripType() {
        if (returnDate == null) return TripType.ONE_WAY;
        else return TripType.ROUND_TRIP;
    }

    @AssertTrue(message = "Return date must be after the departure date.")
    public boolean isReturnDateValid() {
        if (returnDate == null) return true; // Allow for one-way trip
        else
            return returnDate.isAfter(departureDate);
    }

    @AssertTrue(message = "Origin & destination cannot be the same.")
    public boolean isDifferentAirports() {
        return !originCode.equalsIgnoreCase(destinationCode);
    }

    @Override
    public String toString() {
        return "FlightSearchRequest{" +
                "originCode='" + originCode + '\'' +
                ", destinationCode='" + destinationCode + '\'' +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FlightSearchRequest that = (FlightSearchRequest) o;
        return Objects.equals(getOriginCode(), that.getOriginCode()) && Objects.equals(getDestinationCode(), that.getDestinationCode()) && Objects.equals(getDepartureDate(), that.getDepartureDate()) && Objects.equals(getReturnDate(), that.getReturnDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOriginCode(), getDestinationCode(), getDepartureDate(), getReturnDate());
    }
}
