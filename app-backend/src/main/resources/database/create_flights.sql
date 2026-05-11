CREATE TABLE flights (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         airline_id BIGINT NOT NULL,
                         flight_number VARCHAR(10) NOT NULL,
                         origin_airport_id BIGINT NOT NULL,
                         destination_airport_id BIGINT NOT NULL,
                         departure_time TIME NOT NULL,
                         arrival_time TIME NOT NULL,
                         duration_minutes INT NOT NULL,
                         stops INT NOT NULL,

                         CONSTRAINT fk_flight_airline
                             FOREIGN KEY (airline_id) REFERENCES airlines(id),

                         CONSTRAINT fk_flight_origin
                             FOREIGN KEY (origin_airport_id) REFERENCES airports(id),

                         CONSTRAINT fk_flight_destination
                             FOREIGN KEY (destination_airport_id) REFERENCES airports(id)
);