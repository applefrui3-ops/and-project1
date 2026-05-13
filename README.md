# and-project1

---

## Intensive course project

### Creating a database:

#### 1. Create user
```angular2html
psql -U postgres -c "CREATE USER hotelapp_user WITH ENCRYPTED PASSWORD '123';"
```

#### 2. Create database with owner
```angular2html
psql -U postgres -c "CREATE DATABASE hotelapp OWNER hotelapp_user;"
```

#### 3. Log in to the database using the created user
```angular2html
psql -U hotelapp_user -d hotelapp
```

#### 4. Create the tables
```angular2html
CREATE TABLE apartments (
id BIGSERIAL PRIMARY KEY,
price_value INTEGER NOT NULL,
currency VARCHAR(10) NOT NULL,
reservation_status VARCHAR(20) NOT NULL
);
CREATE TABLE clients (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
apartment_id BIGINT REFERENCES apartments(id) ON DELETE SET NULL
);
```

#### 5. Populate the tables with test data
```angular2html
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
```

#### 6. Examples of requests can be found in the [http-requests.md](http-requests.md) file