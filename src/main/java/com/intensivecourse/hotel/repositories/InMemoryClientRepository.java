package com.intensivecourse.hotel.repositories;



import com.intensivecourse.hotel.models.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryClientRepository implements ClientRepository {

    private final Map<Long, Client> storage;

    public InMemoryClientRepository(){
        this.storage = new ConcurrentHashMap<>();
    }

    public InMemoryClientRepository(Map<Long, Client> clients){
        this.storage = clients;
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Client client) {
        storage.put(client.getId(), client);
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        return storage.containsKey(id);
    }
}
