package com.flightcomparison.appbackend.repository;

import com.flightcomparison.appbackend.model.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByIataCodeIgnoreCase(String iataCode);

    List<Airline> findByNameContainingIgnoreCase(String name);

    List<Airline> findAllByOrderByNameAsc();

}
