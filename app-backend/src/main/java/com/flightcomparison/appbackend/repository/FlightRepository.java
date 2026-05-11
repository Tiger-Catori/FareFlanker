package com.flightcomparison.appbackend.repository;

import com.flightcomparison.appbackend.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Derived query – works because Flight has origin.iataCode and destination.iataCode
    List<Flight> findByOriginIataCodeAndDestinationIataCode(String originIata, String destIata);

    // Same but with JOIN FETCH to avoid N+1 when accessing airline & airports
    @Query("SELECT DISTINCT f FROM Flight f " +
            "JOIN FETCH f.airline " +
            "JOIN FETCH f.origin " +
            "JOIN FETCH f.destination " +
            "WHERE f.origin.iataCode = :originIata AND f.destination.iataCode = :destIata")
    List<Flight> findByOriginIataCodeAndDestinationIataCodeWithFetch(@Param("originIata") String originIata,
                                                                     @Param("destIata") String destIata);

    Optional<Flight> findByFlightNumber(String flightNumber);
}