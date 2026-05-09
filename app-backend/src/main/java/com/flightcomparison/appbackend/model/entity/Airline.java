package com.flightcomparison.appbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "airlines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Airline name is required")
    @Column(name = "airline_name", nullable = false, unique = true, length = 100)
    @ToString.Include
    private String name;

    @NotBlank(message = "IATA code is required")
    @Size(min = 2, max = 2, message = "IATA code must be exactly 2 characters")
    @Pattern(regexp = "[A-Z]{2}", message = "IATA code must be 2 uppercase letters")
    @Column(name = "iata_code", nullable = false, unique = true, length = 2)
    @ToString.Include
    private String iataCode;

    // Custom setter for IATA code normalization
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode != null ? iataCode.trim().toUpperCase() : null;
    }

    // Static factory method (alternative to constructor)
    public static Airline create(String name, String iataCode) {
        Airline airline = new Airline();
        airline.setName(name);
        airline.setIataCode(iataCode);
        return airline;
    }
}