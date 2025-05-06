CREATE TABLE customers (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       phone_number VARCHAR(10) NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       full_name VARCHAR(255) NOT NULL
);
