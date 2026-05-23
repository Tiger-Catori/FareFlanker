package com.flightcomparison.appbackend.controller;

import com.flightcomparison.appbackend.exception.ResourceNotFoundException;
import com.flightcomparison.appbackend.model.dto.AirportSuggestionDTO;
import com.flightcomparison.appbackend.model.entity.Airport;
import com.flightcomparison.appbackend.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<AirportSuggestionDTO>> autocomplete(@RequestParam("query") String query) {
        List<AirportSuggestionDTO> suggestions = airportService.suggestAirports(query);
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/{iataCode}")
    public ResponseEntity<AirportSuggestionDTO> getAirportByIata(@PathVariable String iataCode) {
        Airport airport = airportService.getAirportByIataCode(iataCode)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found: " + iataCode));
        AirportSuggestionDTO dto = AirportSuggestionDTO.builder()
                .iataCode(airport.getIataCode())
                .airportName(airport.getName())
                .city(airport.getCity())
                .country(airport.getCountry())
                .build();
        return ResponseEntity.ok(dto);
    }


}
