package legacy.repositories;



import com.intensivecourse.hotel.models.Apartment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryApartmentRepository implements ApartmentRepository {

    private final Map<Long, Apartment> storage;

    public InMemoryApartmentRepository(){
        this.storage = new ConcurrentHashMap<>();
    }

    public InMemoryApartmentRepository(Map<Long, Apartment> storage){
        this.storage = storage;
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
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(long id){
        return storage.containsKey(id);
    }

}
