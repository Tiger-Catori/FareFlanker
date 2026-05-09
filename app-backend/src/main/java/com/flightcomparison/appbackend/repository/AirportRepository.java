package com.flightcomparison.appbackend.repository;

import com.flightcomparison.appbackend.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    List<Airport> findByIataCodeContainingIgnoreCase(String iataCode);
    List<Airport> findByNameContainingIgnoreCase(String name);
    List<Airport> findByCityContainingIgnoreCase(String city);

    @Query("SELECT a FROM Airport a WHERE " +
            "LOWER(a.iataCode) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.city) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Airport> searchByQuery(@Param("query") String query);

}
