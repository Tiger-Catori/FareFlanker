package com.flightcomparison.appbackend.repository;

import com.flightcomparison.appbackend.model.entity.Airport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    // Exact match for validation
    Optional<Airport> findByIataCodeIgnoreCase(String iataCode);

    // Partial IATA prefix (autocomplete)
    List<Airport> findByIataCodeStartingWithIgnoreCase(String iataCodePrefix, Pageable pageable);

    // Combined search with relevance and pagination
    @Query("SELECT a FROM Airport a WHERE " +
            "LOWER(a.iataCode) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.city) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY CASE WHEN LOWER(a.iataCode) = LOWER(:query) THEN 1 " +
            "               WHEN LOWER(a.iataCode) LIKE LOWER(CONCAT(:query, '%')) THEN 2 " +
            "               ELSE 3 END, a.name ASC")
    List<Airport> searchByQuery(@Param("query") String query, Pageable pageable);
}