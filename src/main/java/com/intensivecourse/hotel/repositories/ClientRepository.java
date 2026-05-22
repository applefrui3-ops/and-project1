package com.intensivecourse.hotel.repositories;

import com.intensivecourse.hotel.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
