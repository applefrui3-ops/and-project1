CREATE TABLE apartments
(
    id                 BIGSERIAL PRIMARY KEY,
    price_value        INTEGER     NOT NULL,
    currency           VARCHAR(10) NOT NULL,
    reservation_status VARCHAR(20) NOT NULL
);
CREATE TABLE clients
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    apartment_id BIGINT       REFERENCES apartments (id) ON DELETE SET NULL
);