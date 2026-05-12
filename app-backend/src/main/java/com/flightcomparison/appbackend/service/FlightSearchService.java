package com.flightcomparison.appbackend.service;

import com.flightcomparison.appbackend.model.dto.FlightResultDTO;
import com.flightcomparison.appbackend.model.dto.FlightSearchRequest;

import java.util.List;


/**
 * Service Interface for flight search operations
 * Provides the core business logic to search for flights based on the given criteria.
 * */
public interface FlightSearchService {

    List<FlightResultDTO> searchFlights(FlightSearchRequest request);
}
