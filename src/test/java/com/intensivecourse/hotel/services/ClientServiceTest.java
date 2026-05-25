package com.intensivecourse.hotel.services;

import com.intensivecourse.hotel.models.Client;
import com.intensivecourse.hotel.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientServiceTest {

    @MockitoBean
    private ClientRepository repository;

    @Autowired
    private ClientService service;


    @Test
    void givenClientsInRepository_whenFindAll_thenReturnsAllClients() {
        Client client1 = new Client(1L, "User1");
        Client client2 = new Client(2L, "User2");
        when(repository.findAll()).thenReturn(List.of(client1, client2));

        List<Client> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("User1", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("User2", result.get(1).getName());
        verify(repository).findAll();
    }

    @Test
    void givenEmptyRepository_whenFindAll_thenReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<Client> result = service.findAll();

        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void givenExistingId_whenFindById_thenReturnsClient() {
        long id = 1L;
        Client client = new Client(id, "User1");
        when(repository.findById(id)).thenReturn(Optional.of(client));

        Client result = service.findById(id);

        assertEquals(1L, result.getId());
        assertEquals("User1", result.getName());
        verify(repository).findById(id);
    }

    @Test
    void givenNonExistingId_whenFindById_thenThrowsException() {
        long id = 100L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.findById(id)
        );

        assertTrue(ex.getMessage().contains(String.valueOf(id)));
        assertTrue(ex.getMessage().contains("not found"));
        verify(repository).findById(id);
    }

    @Test
    void givenClient_whenSaveClient_thenDelegatesToRepository() {
        Client client = new Client(1L, "User1");

        service.save(client);

        verify(repository).save(client);
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
        when(repository.existsById(100L)).thenReturn(false);

        boolean result = service.existsById(100L);

        assertFalse(result);
        verify(repository).existsById(100L);
    }
}
