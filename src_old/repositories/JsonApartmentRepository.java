package repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Apartment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class JsonApartmentRepository implements ApartmentRepository {

    private final File file;
    private final ObjectMapper mapper;
    private final Map<Long, Apartment> storage;

    public JsonApartmentRepository(String fileName) {
        this.file = new File(fileName);
        this.mapper = new ObjectMapper();
        this.storage = new ConcurrentHashMap<>();
        load();
    }

    public JsonApartmentRepository(String fileName, Map<Long, Apartment> storage) {
        this.file = new File(fileName);
        this.mapper = new ObjectMapper();
        this.storage = storage;
        load();
    }

    private void load() {
        if (!file.exists()) {
            return;
        }
        try {
            List<Apartment> apartments = mapper.readValue(file, new TypeReference<List<Apartment>>() {
            });
            for (Apartment apartment : apartments) {
                storage.put(apartment.getId(), apartment);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data from " + file.getName(), e);
        }
    }

    private void save(){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>(storage.values()));
        }catch (IOException e){
            throw new RuntimeException("Failed to save data to " + file.getName(), e);
        }
    }

    @Override
    public Optional<Apartment> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Apartment> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Apartment apartment) {
        storage.put(apartment.getId(), apartment);
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
