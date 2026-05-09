package com.flightcomparison.appbackend.model.entity;

import com.flightcomparison.appbackend.model.enums.CabinClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

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

    @NotBlank(message = "Provider is required")
    @Column(nullable = false, length = 100)
    @ToString.Include
    private String provider;

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

    // Convenience constructor
    public FlightPrice(Flight flight, String provider, BigDecimal priceGbp,
                       CabinClass cabinClass, boolean baggageIncluded, boolean refundable) {
        this.flight = flight;
        this.provider = provider;
        this.priceGbp = priceGbp;
        this.cabinClass = cabinClass;
        this.baggageIncluded = baggageIncluded;
        this.refundable = refundable;
    }
}