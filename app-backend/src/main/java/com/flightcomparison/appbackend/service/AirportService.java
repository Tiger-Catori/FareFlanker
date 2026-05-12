package com.flightcomparison.appbackend.service;

import com.flightcomparison.appbackend.model.dto.AirportSuggestionDTO;
import com.flightcomparison.appbackend.model.entity.Airport;

import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for airport-related operations,
 * primarily airport autocomplete (FR-03)
 * and fetching airport details by IATA code.
 */
public interface AirportService {

    List<AirportSuggestionDTO> suggestAirports(String query);

    Optional<Airport> getAirportByIataCode(String iataCode);
}
