package com.flightcomparison.appbackend.service;

import com.flightcomparison.appbackend.exception.ResourceNotFoundException;
import com.flightcomparison.appbackend.model.dto.FlightResultDTO;
import com.flightcomparison.appbackend.model.dto.FlightSearchRequest;
import com.flightcomparison.appbackend.model.dto.RoundTripFlightResultDTO;
import com.flightcomparison.appbackend.model.entity.Flight;
import com.flightcomparison.appbackend.model.entity.FlightPrice;
import com.flightcomparison.appbackend.model.enums.CabinClass;
import com.flightcomparison.appbackend.model.enums.CurrencyType;
import com.flightcomparison.appbackend.model.enums.TripType;
import com.flightcomparison.appbackend.repository.FlightPriceRepository;
import com.flightcomparison.appbackend.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
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

    public FlightSearchServiceImpl(FlightRepository flightRepository, FlightPriceRepository flightPriceRepository, CurrencyConversionService currencyConversionService) {
        this.flightRepository = flightRepository;
        this.flightPriceRepository = flightPriceRepository;
        this.currencyConversionService = currencyConversionService;
    }


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

    @Override
    public List<RoundTripFlightResultDTO> searchRoundTrip(FlightSearchRequest request) {
        // Validating the TripType is a RoundTrip
        if (request.getTripType() != TripType.ROUND_TRIP) {
            throw new IllegalArgumentException("This method is only for round trip searches");
        }

        if (request.getReturnDate() == null || request.getReturnDate().isBefore(request.getDepartureDate())) {
            throw new IllegalArgumentException("Invalid return date");
        }

        // 1. Find outbound flights & prices for departure date
        List<Flight> outboundFlights = flightRepository.findByOriginIataCodeAndDestinationIataCode(
                request.getOriginIata(), request.getDestinationIata());
        if (outboundFlights.isEmpty()) return List.of();

        List<FlightPrice> outboundPrices = flightPriceRepository.findByFlightInAndDepartureDate(
                outboundFlights, request.getDepartureDate());
        if (outboundPrices.isEmpty()) return List.of();

        // 2. Filter outbound by cabin class
        List<FlightPrice> filteredOutbound = outboundPrices.stream()
                .filter(fp -> request.getCabinClass() == null || fp.getCabinClass() == request.getCabinClass())
                .toList();

        // 3. Build outbound DTOs
        List<FlightResultDTO> outBoundLegs = buildFlightResultDTOs(filteredOutbound, String.valueOf(request.getCurrency()));

        // 4. Find inbound flights & prices (swap origin/destination) for return date
        List<Flight> inboundFlights = flightRepository.findByOriginIataCodeAndDestinationIataCode(
                request.getDestinationIata(), request.getOriginIata());
        if (inboundFlights.isEmpty()) return List.of();

        List<FlightPrice> inboundPrices = flightPriceRepository.findByFlightInAndDepartureDate(
                inboundFlights, request.getReturnDate());
        if (inboundPrices.isEmpty()) return List.of();

        List<FlightPrice> filteredInbound = inboundPrices.stream()
                .filter(fp -> request.getCabinClass() == null || fp.getCabinClass() == request.getCabinClass())
                .toList();

        List<FlightResultDTO> inboundLegs = buildFlightResultDTOs(filteredInbound, String.valueOf(request.getCurrency()));

        // 5. Pair each outbound with each inbound (Cartesian product)
        List<RoundTripFlightResultDTO> roundTrips = new ArrayList<>();
        for (FlightResultDTO out : outBoundLegs) {
            for (FlightResultDTO in : inboundLegs) {
                BigDecimal total = out.getPrice().add(in.getPrice());
                int totalDuration = out.getDurationMinutes() + in.getDurationMinutes();
                roundTrips.add(RoundTripFlightResultDTO.builder()
                        .outboundLeg(out)
                        .inboundLeg(in)
                        .totalPrice(total)
                        .totalDurationMinutes(totalDuration)
                        .build());
            }
        }
        // 6. Sort by total price (ascending)
        roundTrips.sort(Comparator.comparing(RoundTripFlightResultDTO::getTotalPrice));
        return roundTrips;
    }

    // Helper method to convert a list of FlightPrice + Flight into FlightResultDTO
    private List<FlightResultDTO> buildFlightResultDTOs(List<FlightPrice> prices, String targetCurrency) {
        List<FlightResultDTO> results = new ArrayList<>();
        for (FlightPrice price : prices) {
            Flight flight = price.getFlight();
            ZonedDateTime departure = ZonedDateTime.of(
                    LocalDateTime.of(price.getDepartureDate(), flight.getDepartureTime()),
                    ZoneId.systemDefault());

            ZonedDateTime arrival = ZonedDateTime.of(
                    LocalDateTime.of(price.getDepartureDate(), flight.getArrivalTime()),
                    ZoneId.systemDefault());

            if (arrival.isBefore(departure)) {
                arrival = arrival.plusDays(1);
            }

            BigDecimal convertedPrice = currencyConversionService.convert(
                    price.getPriceGbp(), "GBP", targetCurrency
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
                    .currency(CurrencyType.valueOf(targetCurrency))
                    .cabinClass(price.getCabinClass())
                    .build();
            results.add(dto);
        }
        return results;
    }

    @Override
    public FlightResultDTO findFlightById(Long flightId, String date) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + flightId));

        // Parse the date string to LocalDate
        LocalDate departureDate = LocalDate.parse(date);

        // Find the price for this flight and date
        FlightPrice price = flightPriceRepository.findByFlightAndDepartureDate(flight, departureDate)
                .orElseThrow(() -> new ResourceNotFoundException("No price found for flight " + flightId + " on date " + date));

        // Build ZonedDateTime using the flight's departure/arrival times and the price's date
        ZonedDateTime departureTime = ZonedDateTime.of(
                LocalDateTime.of(departureDate, flight.getDepartureTime()),
                ZoneId.systemDefault()
        );
        ZonedDateTime arrivalTime = ZonedDateTime.of(
                LocalDateTime.of(departureDate, flight.getArrivalTime()),
                ZoneId.systemDefault()
        );
        if (arrivalTime.isBefore(departureTime)) {
            arrivalTime = arrivalTime.plusDays(1);
        }

        // Optionally convert currency if needed (default to GBP or use a request param)
        BigDecimal convertedPrice = currencyConversionService.convert(
                price.getPriceGbp(), "GBP", "GBP" // or let frontend pass currency
        );

        return FlightResultDTO.builder()
                .airlineName(flight.getAirline().getName())
                .flightNumber(flight.getFlightNumber())
                .originIata(flight.getOrigin().getIataCode())
                .originAirportName(flight.getOrigin().getName())
                .originCity(flight.getOrigin().getCity())
                .destinationIata(flight.getDestination().getIataCode())
                .destinationAirportName(flight.getDestination().getName())
                .destinationCity(flight.getDestination().getCity())
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .durationMinutes(flight.getDurationMinutes())
                .stops(flight.getStops())
                .price(convertedPrice)
                .currency(CurrencyType.GBP) // or from request
                .cabinClass(price.getCabinClass())
                .build();
    }
}
//    @Override
//    public FlightResultDTO findFlightById(Long flightId) {
//        Flight flight = flightRepository.findById(flightId)
//                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + flightId));
//
//        // Return basic flight information (no price/currency because no date is provided)
//        return FlightResultDTO.builder()
//                .airlineName(flight.getAirline().getName())
//                .flightNumber(flight.getFlightNumber())
//                .originIata(flight.getOrigin().getIataCode())
//                .originAirportName(flight.getOrigin().getName())
//                .originCity(flight.getOrigin().getCity())
//                .destinationIata(flight.getDestination().getIataCode())
//                .destinationAirportName(flight.getDestination().getName())
//                .destinationCity(flight.getDestination().getCity())
//                .departureTime(null)
//                .arrivalTime(null)
//                .durationMinutes(flight.getDurationMinutes())
//                .stops(flight.getStops())
//                .price(null)
//                .currency(null)
//                .cabinClass(null)
//                .build();
//    }
//}
