package com.intensivecourse.hotel.repositories;

import com.intensivecourse.hotel.models.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    @EntityGraph(attributePaths = {"apartment"})
    List<Client> findAll();

    @Override
    @EntityGraph(attributePaths = {"apartment"})
    Optional<Client> findById(Long id);
}
