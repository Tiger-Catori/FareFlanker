CREATE TABLE flight_prices (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               flight_id BIGINT NOT NULL,
                               price_gbp DECIMAL(10, 2) NOT NULL,
                               cabin_class VARCHAR(30) NOT NULL,
                               baggage_included BOOLEAN NOT NULL,
                               refundable BOOLEAN NOT NULL,
                               departure_date DATE NOT NULL,

                               CONSTRAINT fk_price_flight FOREIGN KEY (flight_id) REFERENCES flights(id)
);

CREATE INDEX idx_flight_prices_departure_date ON flight_prices(departure_date);
CREATE INDEX idx_flight_prices_route_date ON flight_prices(flight_id, departure_date);