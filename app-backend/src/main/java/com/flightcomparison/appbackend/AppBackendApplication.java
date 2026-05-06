package com.flightcomparison.appbackend;

import com.flightcomparison.appbackend.model.dto.AirportSuggestionDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppBackendApplication.class, args);

//        AirportSuggestionDTO dto = AirportSuggestionDTO.builder()
//                .iataCode("CDG")
//                .airportName("Charles de Gaulle Airport")
//                .city("Paris")
//                .country("France")
//                .build();
    }

}
