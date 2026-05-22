package legacy.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intensivecourse.hotel.models.Client;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class JsonClientRepository implements ClientRepository {

    private final File file;
    private final ObjectMapper mapper;
    private final Map<Long, Client> storage;


    public JsonClientRepository(String fileName) {
        this.file = new File(fileName);
        this.mapper = new ObjectMapper();
        this.storage = new ConcurrentHashMap<>();
        load();
    }

    public JsonClientRepository(String fileName, Map<Long, Client> storage) {
        this.file = new File(fileName);
        this.mapper = new ObjectMapper();
        this.storage = storage;
    }

    private void load() {
        if (!file.exists()) {
            return;
        }
        try {
            List<Client> clients = mapper.readValue(file, new TypeReference<List<Client>>() {
            });
            for (Client client : clients) {
                storage.put(client.getId(), client);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data from " + file.getName(), e);
        }
    }

    private void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>(storage.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save data to " + file.getName(), e);
        }
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
        save();
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
        save();
    }

    @Override
    public boolean existsById(long id) {
        return storage.containsKey(id);
    }
}
