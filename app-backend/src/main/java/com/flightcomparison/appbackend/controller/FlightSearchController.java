package com.flightcomparison.appbackend.controller;

import com.flightcomparison.appbackend.model.dto.FlightResultDTO;
import com.flightcomparison.appbackend.model.dto.FlightSearchRequest;
import com.flightcomparison.appbackend.model.dto.RoundTripFlightResultDTO;
import com.flightcomparison.appbackend.model.enums.TripType;
import com.flightcomparison.appbackend.service.CurrencyConversionService;
import com.flightcomparison.appbackend.service.FlightSearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/flights")
public class FlightSearchController {
    private final FlightSearchService flightSearchService;
    // private final CurrencyConversionService currencyConversionService;

    public FlightSearchController(FlightSearchService flightSearchService, CurrencyConversionService currencyConversionService) {
        this.flightSearchService = flightSearchService;
        // this.currencyConversionService = currencyConversionService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchFlights(@Valid @RequestBody FlightSearchRequest request) {
        if (request.getTripType() == TripType.ONE_WAY) {
            List<FlightResultDTO> results = flightSearchService.searchOneWay(request);
            return ResponseEntity.ok(results);
        } else {
            List<RoundTripFlightResultDTO> results = flightSearchService.searchRoundTrip(request);
            return ResponseEntity.ok(results);
        }
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResultDTO> getFlightById(@PathVariable Long flightId, @RequestParam String date) {
        // Note: This method would require a dedicated service method.
        // For simplicity, we assume FlightSearchService has a findById method.
        FlightResultDTO result = flightSearchService.findFlightById(flightId, date);
        return ResponseEntity.ok(result);
    }

    @GetMapping("health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Flight search service is operational");
    }

}
