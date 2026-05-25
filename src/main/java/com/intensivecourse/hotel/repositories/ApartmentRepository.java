package com.intensivecourse.hotel.repositories;

import com.intensivecourse.hotel.models.Apartment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Override
    @EntityGraph(attributePaths = {"clients"})
    List<Apartment> findAll();

    @Override
    @EntityGraph(attributePaths = {"clients"})
    Optional<Apartment> findById(Long id);
}
