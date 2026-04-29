package repositories;

import models.Apartment;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository {
    Optional<Apartment> findById(long id);
    List<Apartment> findAll();
    void save(Apartment apartment);
    void deleteById(long id);
    boolean existsById(long id);
}
