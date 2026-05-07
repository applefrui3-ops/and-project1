package com.intensivecourse.hotel.repositories;



import com.intensivecourse.hotel.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(long id);
    List<Client> findAll();
    void save(Client client);
    void deleteById(long id);
    boolean existsById(long id);
}
