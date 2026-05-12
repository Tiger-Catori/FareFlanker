package com.flightcomparison.appbackend.service;

import com.flightcomparison.appbackend.model.dto.AirportSuggestionDTO;
import com.flightcomparison.appbackend.model.entity.Airport;
import com.flightcomparison.appbackend.repository.AirportRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Implements airport autocomplete logic using AirportRepository.
 * Converts Airport entities to AirportSuggestionDTO
 * and applies custom sorting for relevance
 */
@Service
@Transactional(readOnly = true)
public class AirportServiceImpl {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    /**
     * Returns autocomplete suggestions based on partial input i.e
     * (IATA code, city name, airport name).
     */
    public List<AirportSuggestionDTO> suggestAirports(String query) {
        // 1. Handle null query
        if (query == null) {
            return Collections.emptyList();
        }

        String trimmedQuery = query.trim();
        if (trimmedQuery.length() < 2) {
            return Collections.emptyList();
        }

        // 2. Fetch up to 20 airports from database.
        Pageable pageable = PageRequest.of(0, 20);
        List<Airport> listAirports = airportRepository.searchByQuery(trimmedQuery, pageable);

        // 3. Remove duplicates while preserving order
        Set<Airport> uniqueAirports = new LinkedHashSet<>(listAirports);
        List<Airport> resultList = new ArrayList<>(uniqueAirports);

        // 4. Normalise query once for scoring
        String normalisedQuery = normalise(trimmedQuery);

        // 5. Sort by relevance score (descending), then by IATA code
        resultList.sort((a1, a2) -> {
            int score1 = getMatchScore(a1, normalisedQuery);
            int score2 = getMatchScore(a2, normalisedQuery);
            if (score1 != score2) {
                return score2 - score1; // higher score first
            }
            // Tie-breaker: alphabetical IATA code
            String iata1 = a1.getIataCode() != null ? a1.getIataCode() : "";
            String iata2 = a2.getIataCode() != null ? a2.getIataCode() : "";
            return iata1.compareTo(iata2);
        });

        // 6. Keep only top 10 resuts
        if (resultList.size() > 10) {
            resultList = resultList.subList(0, 10);
        }

        // 7. Mapping airport entities to AirportSuggestionDTOs
        List<AirportSuggestionDTO> suggestions = new ArrayList<>();
        for (Airport airport : resultList) {
            AirportSuggestionDTO dto = AirportSuggestionDTO.builder()
                    .iataCode(airport.getIataCode())
                    .airportName(airport.getName())
                    .city(airport.getCity())
                    .country(airport.getCountry())
                    .build();
            suggestions.add(dto);
        }
        return suggestions;
    }

    public Optional<Airport> getAirportByIataCode(String iataCode) {
        if (iataCode == null || iataCode.isBlank()) {
            return Optional.empty();
        }
        return airportRepository.findByIataCodeIgnoreCase(iataCode.trim());
    }

    public int getMatchScore(Airport airport, String query) {
        if (airport == null || query == null || query.isBlank()) {
            return 0;
        }

        String newQuery = normalise(query);

        String iata = normalise(airport.getIataCode());
        String city = normalise(airport.getCity());
        String airportName = normalise(airport.getName());

        int score = 0;

        // Exact matches
        if (iata.equals(newQuery)) score += 100;
        if (city.equals(newQuery)) score += 90;
        if (airportName.equals(newQuery)) score += 80;

        // Starts with matches
        if (iata.startsWith(newQuery)) score += 70;
        if (city.startsWith(newQuery)) score += 60;
        if (airportName.startsWith(newQuery)) score += 50;

        // Contains matches
        if (city.contains(newQuery)) score += 40;
        if (airportName.contains(newQuery)) score += 30;

        return score;
    }


    private String normalise(String value) {
        String normalised = value == null ? "" : value.trim().toLowerCase();
        return normalised;
    }

}
