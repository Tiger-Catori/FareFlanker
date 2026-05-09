package com.flightcomparison.appbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "IATA code is required")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters")
    @Pattern(regexp = "[A-Z]{3}", message = "IATA code must be 3 uppercase letters")
    @Column(name = "iata_code", nullable = false, unique = true, length = 3)
    @ToString.Include
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    @Column(name = "airport_name", nullable = false, length = 255)
    @ToString.Include
    private String name;

    @NotBlank(message = "City name is required")
    @Column(nullable = false, length = 100)
    @ToString.Include
    private String city;

    @NotBlank(message = "Country name is required")
    @Column(nullable = false, length = 100)
    @ToString.Include
    private String country;

    // Custom setter for IATA code normalization
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode != null ? iataCode.trim().toUpperCase() : null;
    }

    // Static factory method (alternative to constructor)
    public static Airport create(String iataCode, String name, String city, String country) {
        Airport airport = new Airport();
        airport.setIataCode(iataCode);
        airport.setName(name);
        airport.setCity(city);
        airport.setCountry(country);
        return airport;
    }
}