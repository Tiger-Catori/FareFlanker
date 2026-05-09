package com.flightcomparison.appbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Flight number is required")
    @Column(name = "flight_number", nullable = false, length = 10)
    @ToString.Include
    private String flightNumber;

    @NotNull(message = "Airline is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    @ToString.Include
    private Airline airline;

    @NotNull(message = "Origin airport is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_airport_id", nullable = false)
    @ToString.Include
    private Airport origin;

    @NotNull(message = "Destination airport is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport_id", nullable = false)
    @ToString.Include
    private Airport destination;

    @NotNull(message = "Departure time is required")
    @Column(name = "departure_time", nullable = false)
    @ToString.Include
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Column(name = "arrival_time", nullable = false)
    @ToString.Include
    private LocalDateTime arrivalTime;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(name = "duration_minutes", nullable = false)
    @ToString.Include
    private Integer durationMinutes;

    @Min(value = 0, message = "Stops cannot be negative")
    @Max(value=2, message = "No more than 2 stops")
    @Column(nullable = false)
    @ToString.Include
    private Integer stops;
}