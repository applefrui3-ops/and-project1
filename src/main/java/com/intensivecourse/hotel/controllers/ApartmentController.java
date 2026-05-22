package com.intensivecourse.hotel.controllers;


import com.intensivecourse.hotel.models.Apartment;
import com.intensivecourse.hotel.services.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService){
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public List<Apartment> findAll() {
        return apartmentService.findAll();
    }

    @GetMapping("/{id}")
    public Apartment findById(@PathVariable Long id) {
        return apartmentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Apartment create(@RequestBody Apartment apartment) {
        return apartmentService.save(apartment);
    }

    @PutMapping("/{id}")
    public Apartment update(@PathVariable Long id, @RequestBody Apartment apartment) {
        apartment.setId(id);
        return apartmentService.save(apartment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        apartmentService.deleteById(id);
    }
}
