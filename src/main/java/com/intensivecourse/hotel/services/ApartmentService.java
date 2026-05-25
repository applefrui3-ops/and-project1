package com.intensivecourse.hotel.services;


import com.intensivecourse.hotel.config.ApartmentProperties;
import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.repositories.ApartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentProperties apartmentProperties;

    public ApartmentService(ApartmentRepository apartmentRepository, ApartmentProperties apartmentProperties) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentProperties = apartmentProperties;
    }

    @Transactional(readOnly = true)
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Apartment findById(long id) throws IllegalArgumentException {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apartment not found: " + id));
    }

    public Apartment save(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public Apartment update(Apartment updated) {
        Apartment existing = findById(updated.getId());

        if (updated.getReservationStatus() == null) {
            updated.setReservationStatus(existing.getReservationStatus());
        }

        if (
                !apartmentProperties.getStatus().isChangeable() &&
                !updated.getReservationStatus().equals(existing.getReservationStatus())
        ) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Status change is disabled in configuration"
            );
        }

        return apartmentRepository.save(updated);
    }

    public void deleteById(long id) {
        apartmentRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return apartmentRepository.existsById(id);
    }


    public String getApartmentStatus(long id) {
        return findById(id).getReservationStatus().name();
    }
}
