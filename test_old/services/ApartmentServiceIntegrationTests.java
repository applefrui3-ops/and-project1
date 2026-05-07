package services;

import models.Apartment;
import models.Currency;
import models.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.ApartmentRepository;
import repositories.InMemoryApartmentRepository;
import util.DataGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApartmentServiceIntegrationTests {

    private DataGenerator dataGenerator;
    private ApartmentRepository apartmentRepository;
    private ApartmentService apartmentService;

    @BeforeEach
    void setUp(){
        this.dataGenerator = new DataGenerator(50, Currency.BYN, ReservationStatus.FREE);
        this.apartmentRepository = new InMemoryApartmentRepository(dataGenerator.generateApartments());
        this.apartmentService = new ApartmentService(apartmentRepository);
    }

    @Test
    void givenApartmentsInRepository_whenFindAll_thenReturnAllApartments(){
        List<Apartment> result = apartmentService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void givenSavedApartment_whenFindById_thenReturnsId() {
        Apartment apartment = new Apartment(1L, null, new ArrayList<>(), ReservationStatus.FREE);
        apartmentService.saveApartment(apartment);

        Apartment result = apartmentService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(ReservationStatus.FREE, result.getReservationStatus());
    }

    @Test
    void givenSavedApartment_whenGetStatus_thenReturnsCorrectStatus() {
        Apartment apartment = new Apartment(1L, null, new ArrayList<>(), ReservationStatus.RESERVED);
        apartmentService.saveApartment(apartment);

        String status = apartmentService.getApartmentStatus(1L);

        assertEquals("RESERVED", status);
    }

    @Test
    void givenTwoSavedApartments_whenFindAll_thenReturnsAll() {
        List<Apartment> apartments = apartmentService.findAll();
        long previousSize = apartments.size();

        Apartment apartment1 = new Apartment(100001L, null, new ArrayList<>(), ReservationStatus.FREE);
        Apartment apartment2 = new Apartment(100002L, null, new ArrayList<>(), ReservationStatus.FREE);
        apartmentService.saveApartment(apartment1);
        apartmentService.saveApartment(apartment2);

        List<Apartment> result = apartmentService.findAll();

        assertEquals(previousSize + 2, result.size());
    }

    @Test
    void givenSavedApartment_whenDeleteById_thenFindByIdThrowsException() {
        Apartment apartment = new Apartment(1L, null, new ArrayList<>(), ReservationStatus.FREE);
        apartmentService.saveApartment(apartment);

        apartmentService.deleteById(1L);

        assertThrows(IllegalArgumentException.class, () -> apartmentService.findById(1L));
    }
}
