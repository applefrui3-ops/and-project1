package services;

import models.Client;
import repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll(){
        return this.clientRepository.findAll();
    }

    public Client findById(long id){
        Optional<Client> optional = clientRepository.findById(id);
        Client client = optional.orElseThrow(
                () -> new IllegalArgumentException("The client with id: " + id + " is not found")
        );
        return client;
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }

    public void deleteById(long id){
        clientRepository.deleteById(id);
    }

    public boolean existsById(long id){
        return clientRepository.existsById(id);
    }

}
