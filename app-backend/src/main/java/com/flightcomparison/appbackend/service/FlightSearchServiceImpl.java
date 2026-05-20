package com.flightcomparison.appbackend.service;

import com.flightcomparison.appbackend.model.dto.FlightResultDTO;
import com.flightcomparison.appbackend.model.dto.FlightSearchRequest;
import com.flightcomparison.appbackend.model.entity.Flight;
import com.flightcomparison.appbackend.model.entity.FlightPrice;
import com.flightcomparison.appbackend.model.enums.CabinClass;
import com.flightcomparison.appbackend.model.enums.CurrencyType;
import com.flightcomparison.appbackend.model.enums.TripType;
import com.flightcomparison.appbackend.repository.FlightPriceRepository;
import com.flightcomparison.appbackend.repository.FlightRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightRepository flightRepository;
    private final FlightPriceRepository flightPriceRepository;
    private final CurrencyConversionService currencyConversionService;

    public FlightSearchServiceImpl (FlightRepository flightRepository, FlightPriceRepository flightPriceRepository, CurrencyConversionService currencyConversionService) {
        this.flightRepository = flightRepository;
        this.flightPriceRepository = flightPriceRepository;
        this.currencyConversionService = currencyConversionService;
    }

//    @Override
//    @Cacheable(value = "flightResults", key = "#request")
//    public List<FlightResultDTO> searchFlights(FlightSearchRequest request) {
//        // Delegate based on trip type
//        if (request.getTripType() == TripType.ONE_WAY) {
//            return;
//        }
//    }

    @Override
    public List<FlightResultDTO> searchOneWay(FlightSearchRequest request) {
        // 1. Find all flights matching the route
        List<Flight> flights = flightRepository.findByOriginIataCodeAndDestinationIataCode(
                request.getOriginIata(), request.getDestinationIata()
        );

        if (flights.isEmpty()) {
            return List.of(); // empty list
        }

        // 2. Find all flight prices for those flights on the given departure date
        List<FlightPrice> flightPrices = flightPriceRepository.findByFlightInAndDepartureDate(
                flights, request.getDepartureDate()
        );

        if (flightPrices.isEmpty()) {
            return List.of();
        }

        // 3. Filter by cabin class if needed
        CabinClass requestedCabin = request.getCabinClass();
        List<FlightPrice> filteredPrices = flightPrices.stream()
                .filter(fp -> requestedCabin == null || fp.getCabinClass() == requestedCabin)
                .toList();

        // 4. Building DTOs & applying currency conversion
        List<FlightResultDTO> results = new ArrayList<>();
        for (FlightPrice price : filteredPrices) {
            Flight flight = price.getFlight();

            // Combine date from FlightPrice and time from flight to created ZonedDateTime
            ZonedDateTime departure = ZonedDateTime.of(
                    LocalDateTime.of(price.getDepartureDate(), flight.getDepartureTime()),
                    ZoneId.systemDefault()
            );

            ZonedDateTime arrival = ZonedDateTime.of(
                    LocalDateTime.of(price.getDepartureDate(), flight.getArrivalTime()),
                    ZoneId.systemDefault()
            );

            // If arrival time is earlier than departure (overnight flight), add one day
            if (arrival.isBefore(departure)) {
                arrival = arrival.plusDays(1);
            }

            // Convert price form GBP to chosen currency
            BigDecimal convertedPrice = currencyConversionService.convert(
                    price.getPriceGbp(), "GBP", String.valueOf(request.getCurrency())
            );

            FlightResultDTO dto = FlightResultDTO.builder()
                    .airlineName(flight.getAirline().getName())
                    .flightNumber(flight.getFlightNumber())
                    .originIata(flight.getOrigin().getIataCode())
                    .originAirportName(flight.getOrigin().getName())
                    .originCity(flight.getOrigin().getCity())
                    .destinationIata(flight.getDestination().getIataCode())
                    .destinationAirportName(flight.getDestination().getName())
                    .destinationCity(flight.getDestination().getCity())
                    .departureTime(departure)
                    .arrivalTime(arrival)
                    .durationMinutes(flight.getDurationMinutes())
                    .stops(flight.getStops())
                    .price(convertedPrice)
                    .currency(CurrencyType.valueOf(String.valueOf(request.getCurrency()))) // need import
                    .cabinClass(price.getCabinClass())
                    .build();

            results.add(dto);
        }

        // 5. Sort results (default: by price, but can be extended from request)
        // For now, sort by price ascending
        results.sort(Comparator.comparing(FlightResultDTO::getPrice));
        return results;
    }
}
