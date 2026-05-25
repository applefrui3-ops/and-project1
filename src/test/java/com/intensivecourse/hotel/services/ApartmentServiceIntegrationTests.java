package com.intensivecourse.hotel.services;

import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.models.Currency;
import com.intensivecourse.hotel.models.Price;
import com.intensivecourse.hotel.models.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ApartmentServiceIntegrationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hotel_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ApartmentService service;

    @Test
    void givenSavedApartment_whenFindById_thenReturnsIt() {
        Apartment apartment = new Apartment();
        apartment.setPrice(new Price(100, Currency.BYN));
        apartment.setReservationStatus(ReservationStatus.FREE);
        Apartment saved = service.save(apartment);

        Apartment result = service.findById(saved.getId());

        assertEquals(saved.getId(), result.getId());
        assertEquals(ReservationStatus.FREE, result.getReservationStatus());
        assertEquals(100, result.getPrice().getValue());
    }

    @Test
    void givenSavedApartment_whenGetStatus_thenReturnsCorrectStatus() {
        Apartment apartment = new Apartment();
        apartment.setPrice(new Price(200, Currency.USD));
        apartment.setReservationStatus(ReservationStatus.RESERVED);
        Apartment saved = service.save(apartment);

        String status = service.getApartmentStatus(saved.getId());

        assertEquals("RESERVED", status);
    }

    @Test
    void givenTwoSavedApartments_whenFindAll_thenReturnsBoth() {
        Apartment apt1 = new Apartment();
        apt1.setPrice(new Price(50, Currency.BYN));
        apt1.setReservationStatus(ReservationStatus.FREE);
        service.save(apt1);

        Apartment apt2 = new Apartment();
        apt2.setPrice(new Price(75, Currency.USD));
        apt2.setReservationStatus(ReservationStatus.OCCUPIED);
        service.save(apt2);

        List<Apartment> result = service.findAll();

        assertTrue(result.size() >= 2);
    }

    @Test
    void givenSavedApartment_whenDeleteById_thenFindByIdThrowsException() {
        Apartment apartment = new Apartment();
        apartment.setPrice(new Price(300, Currency.BYN));
        apartment.setReservationStatus(ReservationStatus.FREE);
        Apartment saved = service.save(apartment);

        service.deleteById(saved.getId());

        assertThrows(IllegalArgumentException.class,
                () -> service.findById(saved.getId()));
    }

    @Test
    void givenSavedApartment_whenExistsById_thenReturnsTrue() {
        Apartment apartment = new Apartment();
        apartment.setPrice(new Price(400, Currency.BYN));
        apartment.setReservationStatus(ReservationStatus.FREE);
        Apartment saved = service.save(apartment);

        boolean exists = service.existsById(saved.getId());

        assertTrue(exists);
    }
}