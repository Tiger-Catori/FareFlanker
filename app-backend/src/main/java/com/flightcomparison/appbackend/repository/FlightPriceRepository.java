package com.flightcomparison.appbackend.repository;

import com.flightcomparison.appbackend.model.enums.CabinClass;
import com.flightcomparison.appbackend.model.entity.FlightPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface FlightPriceRepository extends JpaRepository<FlightPrice, Long> {

    // Core method: find prices by origin, destination, departure date (no seat check)
    @EntityGraph(attributePaths = {"flight", "flight.airline", "flight.origin", "flight.destination"})
    List<FlightPrice> findByFlightOriginIataCodeAndFlightDestinationIataCodeAndDepartureDate(
            String originIata, String destIata, LocalDate departureDate);

    // With cabin class filter
    @EntityGraph(attributePaths = {"flight", "flight.airline", "flight.origin", "flight.destination"})
    List<FlightPrice> findByFlightOriginIataCodeAndFlightDestinationIataCodeAndDepartureDateAndCabinClass(
            String originIata, String destIata, LocalDate departureDate, CabinClass cabinClass);

    // For return trips: fetch prices for multiple dates (outbound and inbound)
    @EntityGraph(attributePaths = {"flight", "flight.airline", "flight.origin", "flight.destination"})
    List<FlightPrice> findByFlightOriginIataCodeAndFlightDestinationIataCodeAndDepartureDateIn(
            String originIata, String destIata, List<LocalDate> departureDates);

    // Alternative: given a list of Flight entities, find prices on a specific date
    @EntityGraph(attributePaths = {"flight", "flight.airline", "flight.origin", "flight.destination"})
    List<FlightPrice> findByFlightInAndDepartureDate(
            List<com.flightcomparison.appbackend.model.entity.Flight> flights, LocalDate departureDate);

    // Custom JPQL with sorting by price or duration (supports pagination) – no seat condition
    @Query("SELECT fp FROM FlightPrice fp " +
            "JOIN FETCH fp.flight f " +
            "JOIN FETCH f.origin o " +
            "JOIN FETCH f.destination d " +
            "JOIN FETCH f.airline a " +
            "WHERE o.iataCode = :originIata AND d.iataCode = :destIata " +
            "AND fp.departureDate = :departureDate " +
            "AND (:cabinClass IS NULL OR fp.cabinClass = :cabinClass) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'price' THEN fp.priceGbp END ASC, " +
            "CASE WHEN :sortBy = 'duration' THEN f.durationMinutes END ASC")
    List<FlightPrice> searchFlightsWithSorting(@Param("originIata") String originIata,
                                               @Param("destIata") String destIata,
                                               @Param("departureDate") LocalDate departureDate,
                                               @Param("cabinClass") CabinClass cabinClass,
                                               @Param("sortBy") String sortBy,
                                               Pageable pageable);
}