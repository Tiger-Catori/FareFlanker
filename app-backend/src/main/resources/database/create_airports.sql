CREATE TABLE airports (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          iata_code VARCHAR(3) NOT NULL UNIQUE,
                          airport_name VARCHAR(255) NOT NULL,
                          city VARCHAR(100) NOT NULL,
                          country VARCHAR(100) NOT NULL
);