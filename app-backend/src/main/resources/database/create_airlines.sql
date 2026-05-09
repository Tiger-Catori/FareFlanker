CREATE TABLE airlines (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          airline_name VARCHAR(100) NOT NULL UNIQUE,
                          iata_code VARCHAR(2) NOT NULL UNIQUE
);