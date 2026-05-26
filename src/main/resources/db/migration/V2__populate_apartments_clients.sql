INSERT INTO apartments (price_value, currency, reservation_status)
VALUES (50, 'USD', 'RESERVED'),
       (50, 'USD', 'FREE'),
       (50, 'USD', 'FREE'),
       (100, 'BYN', 'RESERVED'),
       (100, 'BYN', 'OCCUPIED');

INSERT INTO clients (name, apartment_id)
VALUES ('user1', 1),
       ('user2', 4),
       ('user3', 5);

INSERT INTO clients (name)
VALUES ('user4'),
       ('user5');