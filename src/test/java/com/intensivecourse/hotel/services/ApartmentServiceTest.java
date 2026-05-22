package com.intensivecourse.hotel.services;

import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.models.ReservationStatus;
import com.intensivecourse.hotel.repositories.ApartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApartmentServiceTest {

    @MockitoBean
    private ApartmentRepository repository;
    @Autowired
    private ApartmentService apartmentService;

    @BeforeEach
    void setUp(){
        this.apartmentRepository = mock(ApartmentRepository.class);
        this.apartmentService = new ApartmentService(apartmentRepository);
    }

    @Test
    void givenApartmentsInRepository_whenFindAll_thenReturnAllApartments(){
        Apartment apartment1 = new Apartment(1L, null, new ArrayList<>(), ReservationStatus.FREE);
        Apartment apartment2 = new Apartment(2L, null, new ArrayList<>(), ReservationStatus.FREE);
        List<Apartment> apartments = List.of(apartment1, apartment2);
        when(apartmentRepository.findAll()).thenReturn(apartments);

        List<Apartment> result = apartmentService.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(apartmentRepository, times(1)).findAll();
    }

    @Test
    void givenEmptyRepository_whenFindAll_thenReturnEmptyList(){
        when(apartmentRepository.findAll()).thenReturn(List.of());

        List<Apartment> result = apartmentService.findAll();

        assertTrue(result.isEmpty());
        verify(apartmentRepository, times(1)).findAll();
    }

    @Test
    void givenExistingId_whenFindById_thenReturnsApartment(){
        long id = 1L;
        Apartment apartment = new Apartment(id, null, new ArrayList<>(), ReservationStatus.FREE);

        when(apartmentRepository.findById(id)).thenReturn(Optional.of(apartment));

        Apartment result = apartmentService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(apartmentRepository, times(1)).findById(id);
    }

    @Test
    void givenNonExistingId_whenFindById_thenThrowsException(){
        long id = 100L;

        when(apartmentRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> apartmentService.findById(id));

        assertTrue(thrown.getMessage().contains(String.valueOf(id)));
        assertTrue(thrown.getMessage().contains("not found"));
        verify(apartmentRepository, times(1)).findById(id);
    }

    @Test
    void givenApartment_whenSaveApartment_thenDelegatesToRepository(){
        Apartment apartment = new Apartment(1L, null, new ArrayList<>(), ReservationStatus.FREE);

        apartmentService.saveApartment(apartment);

        verify(apartmentRepository, times(1)).save(apartment);
    }

    @Test
    void givenId_whenDeleteById_thenDelegatesToRepository(){
        long id = 1L;

        apartmentService.deleteById(id);

        verify(apartmentRepository, times(1)).deleteById(id);
    }

    @Test
    void givenExistingId_whenExistsById_thenReturnsTrue(){
        long id = 1L;

        when(apartmentRepository.existsById(id)).thenReturn(true);

        boolean result = apartmentService.existsById(id);

        assertTrue(result);
        verify(apartmentRepository, times(1)).existsById(id);
    }

    @Test
    void givenNonExistingId_whenExistsById_thenReturnsFalse(){
        long id = 100L;

        when(apartmentRepository.existsById(id)).thenReturn(false);

        boolean result = apartmentService.existsById(id);

        assertFalse(result);
        verify(apartmentRepository, times(1)).existsById(id);
    }

    @Test
    void givenReservedApartment_whenGetApartmentStatus_thenReturnsReserved(){
        long id = 1L;
        Apartment apartment = new Apartment(id, null, new ArrayList<>(), ReservationStatus.RESERVED);

        when(apartmentRepository.findById(id)).thenReturn(Optional.of(apartment));

        String result = apartmentService.getApartmentStatus(id);

        assertEquals("RESERVED", result);
        verify(apartmentRepository, times(1)).findById(id);
    }

    @Test
    void givenFreeApartment_whenGetApartmentStatus_thenReturnsFree(){
        long id = 1L;
        Apartment apartment = new Apartment(id, null, new ArrayList<>(), ReservationStatus.FREE);

        when(apartmentRepository.findById(id)).thenReturn(Optional.of(apartment));

        String result = apartmentService.getApartmentStatus(id);

        assertEquals("FREE", result);
        verify(apartmentRepository, times(1)).findById(id);
    }

    @Test
    void givenNonExistingId_whenGetApartmentStatus_thenThrowsException(){
        long id = 100L;

        when(apartmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> apartmentService.getApartmentStatus(id));

        verify(apartmentRepository, times(1)).findById(id);
    }

}
