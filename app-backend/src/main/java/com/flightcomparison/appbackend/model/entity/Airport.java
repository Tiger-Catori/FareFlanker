package com.flightcomparison.appbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "IATA code is requried")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters.")
    @Pattern(regexp = "[A-Z]{3}", message = "IATA code must be 3 uppercase letters")
    @Column(name = "iata_code", nullable = false, unique = true, length = 3)
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "City name is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Country name is required")
    @Column(nullable = false)
    private String country;

    // ----------- Constructors------------
    public Airport() {
    }

    public Airport(String iataCode, String name, String city, String country) {
        this.iataCode = iataCode.toUpperCase();
        this.name = name;
        this.city = city;
        this.country = country;
    }

    // ---------- Getters and Setters ----------
    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        // Normalise to uppercase and trim whitespace
        this.iataCode = iataCode != null ? iataCode.trim().toUpperCase() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", iataCode='" + iataCode + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        // If ID exists, use it; otherwise fall back to IATA code (logical key)
        if (id != null && airport.id != null) {
            return Objects.equals(getId(), airport.getId());
        }
        return Objects.equals(getIataCode(), airport.getIataCode());
    }

    @Override
    public int hashCode() {
        int result = id != null ? Objects.hash(id) : Objects.hash(iataCode);
        return result;
    }
}
