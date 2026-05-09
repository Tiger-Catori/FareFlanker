CREATE TABLE flight_prices (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               flight_id BIGINT NOT NULL,
                               provider VARCHAR(100) NOT NULL,
                               price_gbp DECIMAL(10, 2) NOT NULL,
                               cabin_class VARCHAR(30) NOT NULL,
                               baggage_included BOOLEAN NOT NULL,
                               refundable BOOLEAN NOT NULL,
                               CONSTRAINT fk_price_flight FOREIGN KEY (flight_id) REFERENCES flights(id)
);