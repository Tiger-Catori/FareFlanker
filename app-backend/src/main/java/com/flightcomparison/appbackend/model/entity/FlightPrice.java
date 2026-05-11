package com.flightcomparison.appbackend.model.entity;

import com.flightcomparison.appbackend.model.enums.CabinClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;  // <-- import this

@Entity
@Table(name = "flight_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlightPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Flight is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    @ToString.Include
    private Flight flight;

    @NotNull(message = "Price in GBP is required")
    @Positive(message = "Price must be positive")
    @Column(name = "price_gbp", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal priceGbp;

    @NotNull(message = "Cabin class is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "cabin_class", nullable = false, length = 30)
    @ToString.Include
    private CabinClass cabinClass;

    @Column(name = "baggage_included", nullable = false)
    @ToString.Include
    private boolean baggageIncluded;

    @Column(nullable = false)
    @ToString.Include
    private boolean refundable;

    // NEW: Departure date (the specific date of travel)
    @NotNull(message = "Departure date is required")
    @Column(name = "departure_date", nullable = false)
    @ToString.Include
    private LocalDate departureDate;

    // Constructor
    public FlightPrice(Flight flight, BigDecimal priceGbp,
                       CabinClass cabinClass, boolean baggageIncluded,
                       boolean refundable, LocalDate departureDate) {
        this.flight = flight;
        this.priceGbp = priceGbp;
        this.cabinClass = cabinClass;
        this.baggageIncluded = baggageIncluded;
        this.refundable = refundable;
        this.departureDate = departureDate;
    }

    public static FlightPrice create(Flight flight, BigDecimal priceGbp,
                                     CabinClass cabinClass, boolean baggageIncluded,
                                     boolean refundable, LocalDate departureDate) {
        return new FlightPrice(flight, priceGbp, cabinClass, baggageIncluded, refundable, departureDate);
    }
}