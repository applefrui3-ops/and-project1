package services;

import models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void setUp(){
        this.clientRepository = mock(ClientRepository.class);
        this.clientService = new ClientService(this.clientRepository);
    }

    @Test
    void givenClientsInRepository_whenFindAll_thenReturnsAllClients(){
        Client client1 = new Client(1L, "User1", null);
        Client client2 = new Client(2L, "User2", null);
        List<Client> clients = List.of(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void givenEmptyRepository_whenFindAll_thenReturnsEmptyList(){
        when(clientRepository.findAll()).thenReturn(List.of());

        List<Client> result = clientService.findAll();

        assertTrue(result.isEmpty());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void givenExistingId_whenFindById_thenReturnsClient(){
        long id = 1L;
        Client client = new Client(id, "User",  null);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Client result = clientService.findById(id);

        assertEquals(1L, result.getId());
        verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void givenNonExistingId_whenFindById_thenThrowsException(){
        long id = 100L;
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> clientService.findById(id));

        assertTrue(thrown.getMessage().contains(String.valueOf(id)));
        assertTrue(thrown.getMessage().contains("not found"));
        verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void givenClient_whenSaveClient_thenDelegatesToRepository(){
        Client client = new Client(1L, "User", null);

        clientService.saveClient(client);

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void givenId_whenDeleteById_thenDelegatesToRepository(){
        long id = 1L;

        clientService.deleteById(id);

        verify(clientRepository, times(1)).deleteById(id);
    }

    @Test
    void givenExistingId_whenExistsById_thenReturnsTrue(){
        long id = 1L;

        when(clientRepository.existsById(id)).thenReturn(true);

        boolean result = clientService.existsById(id);

        assertTrue(result);
        verify(clientRepository, times(1)).existsById(id);

    }

    @Test
    void givenNonExistingId_whenExistsById_thenReturnsFalse(){
        long id = 100L;

        when(clientRepository.existsById(id)).thenReturn(false);

        boolean result = clientService.existsById(id);

        assertFalse(result);
        verify(clientRepository, times(1)).existsById(id);
    }

}
