package com.intensivecourse.hotel.services;

import com.intensivecourse.hotel.config.ApartmentProperties;
import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.models.ReservationStatus;
import com.intensivecourse.hotel.repositories.ApartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {

    @Mock
    private ApartmentRepository repository;
    @Mock
    private ApartmentProperties properties;
    @Mock
    private ApartmentProperties.Status statusSettings;
    @Mock
    private ApartmentProperties.Base base;

    @InjectMocks
    private ApartmentService service;



    @BeforeEach
    void setUp() {
        lenient().when(properties.getBase()).thenReturn(base);
        lenient().when(properties.getStatus()).thenReturn(statusSettings);
    }

    @Test
    void givenApartmentsInRepository_whenFindAll_thenReturnAllApartments() {
        Apartment apt1 = new Apartment();
        apt1.setId(1L);
        Apartment apt2 = new Apartment();
        apt2.setId(2L);
        when(repository.findAll()).thenReturn(List.of(apt1, apt2));

        List<Apartment> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void givenEmptyRepository_whenFindAll_thenReturnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<Apartment> result = service.findAll();

        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void givenExistingId_whenFindById_thenReturnsApartment() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(apartment));

        Apartment result = service.findById(1L);

        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
    }

    @Test
    void givenNonExistingId_whenFindById_thenThrowsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.findById(99L));
        verify(repository).findById(99L);
    }


    @Test
    void givenApartment_whenSave_thenReturnsSavedApartment() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        when(repository.save(apartment)).thenReturn(apartment);

        Apartment result = service.save(apartment);

        assertEquals(1L, result.getId());
        verify(repository).save(apartment);
    }

    @Test
    void givenId_whenDeleteById_thenDelegatesToRepository() {
        service.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void givenExistingId_whenExistsById_thenReturnsTrue() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean result = service.existsById(1L);

        assertTrue(result);
        verify(repository).existsById(1L);
    }

    @Test
    void givenNonExistingId_whenExistsById_thenReturnsFalse() {
        when(repository.existsById(99L)).thenReturn(false);

        boolean result = service.existsById(99L);

        assertFalse(result);
        verify(repository).existsById(99L);
    }

    @Test
    void givenApartmentWithStatus_whenGetApartmentStatus_thenReturnsStatus() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setReservationStatus(ReservationStatus.RESERVED);
        when(repository.findById(1L)).thenReturn(Optional.of(apartment));

        String status = service.getApartmentStatus(1L);

        assertEquals("RESERVED", status);
        verify(repository).findById(1L);
    }

    @Test
    void givenStatusChangeEnabledAndStatusChanged_whenUpdate_thenSucceeds() {
        when(statusSettings.isChangeable()).thenReturn(true);

        Apartment existing = new Apartment();
        existing.setId(1L);
        existing.setReservationStatus(ReservationStatus.FREE);

        Apartment updated = new Apartment();
        updated.setId(1L);
        updated.setReservationStatus(ReservationStatus.RESERVED);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(updated);

        Apartment result = service.update(updated);

        assertEquals(ReservationStatus.RESERVED, result.getReservationStatus());
        verify(repository).save(updated);
    }

    @Test
    void givenStatusChangeEnabledAndStatusNotChanged_whenUpdate_thenSucceeds() {
        when(statusSettings.isChangeable()).thenReturn(true);

        Apartment existing = new Apartment();
        existing.setId(1L);
        existing.setReservationStatus(ReservationStatus.FREE);

        Apartment updated = new Apartment();
        updated.setId(1L);
        updated.setReservationStatus(ReservationStatus.FREE);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(updated);

        Apartment result = service.update(updated);

        assertEquals(ReservationStatus.FREE, result.getReservationStatus());
        verify(repository).save(updated);
    }

    @Test
    void givenStatusChangeDisabledAndStatusChanged_whenUpdate_thenThrowsForbidden() {
        when(statusSettings.isChangeable()).thenReturn(false);

        Apartment existing = new Apartment();
        existing.setId(1L);
        existing.setReservationStatus(ReservationStatus.FREE);

        Apartment updated = new Apartment();
        updated.setId(1L);
        updated.setReservationStatus(ReservationStatus.RESERVED);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.update(updated));

        assertEquals(403, ex.getStatusCode().value());
        verify(repository, never()).save(any());
    }

    @Test
    void givenStatusChangeDisabledAndStatusNotChanged_whenUpdate_thenSucceeds() {
        when(statusSettings.isChangeable()).thenReturn(false);

        Apartment existing = new Apartment();
        existing.setId(1L);
        existing.setReservationStatus(ReservationStatus.FREE);

        Apartment updated = new Apartment();
        updated.setId(1L);
        updated.setReservationStatus(ReservationStatus.FREE);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(updated);

        Apartment result = service.update(updated);

        assertEquals(ReservationStatus.FREE, result.getReservationStatus());
        verify(repository).save(updated);
    }

    @Test
    void givenStatusChangeDisabledAndStatusNotProvided_whenUpdate_thenSucceeds() {
        when(statusSettings.isChangeable()).thenReturn(false);

        Apartment existing = new Apartment();
        existing.setId(1L);
        existing.setReservationStatus(ReservationStatus.FREE);

        Apartment updated = new Apartment();
        updated.setId(1L);
        updated.setReservationStatus(null);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(updated);

        Apartment result = service.update(updated);

        assertEquals(ReservationStatus.FREE, result.getReservationStatus());
        verify(repository).save(updated);
    }
}
