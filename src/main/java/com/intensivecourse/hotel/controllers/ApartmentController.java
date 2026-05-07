package com.intensivecourse.hotel.controllers;


import com.intensivecourse.hotel.models.*;
import com.intensivecourse.hotel.models.Currency;
import com.intensivecourse.hotel.services.ApartmentService;
import com.intensivecourse.hotel.services.ClientService;

import java.util.*;

public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ClientService clientService;
    private final Currency baseCurrency;
    private final int basePrice;
    private final ReservationStatus baseStatus;

    public ApartmentController(ApartmentService apartmentService,
                               ClientService clientService,
                               int basePrice,
                               Currency baseCurrency,
                               ReservationStatus baseStatus) {
        this.apartmentService = apartmentService;
        this.clientService = clientService;
        this.basePrice = basePrice;
        this.baseCurrency = baseCurrency;
        this.baseStatus = baseStatus;
    }

    private void printApartment(Apartment apartment) {
        System.out.println("-------");
        System.out.println("Number: " + apartment.getId());
        System.out.println("Price: " + apartment.getPrice().getValue()
                + " " + apartment.getPrice().getCurrency());
        System.out.println("Status: " + apartment.getReservationStatus());
        System.out.println("Residents: " + apartment.getClients());
        System.out.println("-------");
    }

    public void showList() {
        apartmentService.findAll().stream()
                .sorted(Comparator.comparingLong(Apartment::getId).reversed())
                .forEach(this::printApartment);
        System.out.println("\n");
    }

    public void add(Scanner scanner){
        System.out.println("Insert apartment number:");
        System.out.print("->");
        long id = Long.parseLong(scanner.next());

        System.out.println("Insert price:");
        System.out.print("->");
        int price = Integer.parseInt(scanner.next());

        Apartment newApartment = new Apartment(id, new Price(price, this.baseCurrency), new ArrayList<>(), this.baseStatus);
        apartmentService.saveApartment(newApartment);

        Apartment apartment = apartmentService.findById(id);
        if(newApartment.equals(apartment)){
            System.out.println("New apartment is created: " + newApartment.toString());
        }else {
            System.out.println("Error: apartment wasn't created");
        }
    }

    public void editStatus(Scanner scanner) {
        System.out.println("Insert the apartment number:");
        System.out.print("->");
        String roomNumber = scanner.next();
        Apartment apartment = apartmentService.findById(Long.parseLong(roomNumber));

        System.out.println("Choose appropriate status for this apartment");
        System.out.println("1. FREE");
        System.out.println("2. RESERVED");
        System.out.println("3. OCCUPIED");
        System.out.print("->");
        int roomStatusId = Integer.parseInt(scanner.next());
        List<ReservationStatus> statuses = List.of(
                ReservationStatus.FREE,
                ReservationStatus.RESERVED,
                ReservationStatus.OCCUPIED
                );
        apartment.setReservationStatus(statuses.get(roomStatusId - 1));
        System.out.println(apartment);
        apartmentService.saveApartment(apartment);
    }

    public void setClient(Scanner scanner){
        System.out.println("Insert the apartment number:");
        System.out.print("->");
        long apartmentId = Long.parseLong(scanner.next());

        System.out.println("Insert client id:");
        System.out.print("->");
        long clientId = Long.parseLong(scanner.next());

        Apartment apartment = apartmentService.findById(apartmentId);
        Client client = clientService.findById(clientId);

        apartment.setClients(List.of(client));

        apartmentService.saveApartment(apartment);
        System.out.println("Apartment with id=" + apartmentId + " updated: " + apartment.toString());
    }

    public void delete(Scanner scanner){
        System.out.println("Insert the apartment number:");
        System.out.print("->");
        long id = Long.parseLong(scanner.next());

        if(!apartmentService.existsById(id)){
            System.out.println("Apartment with id=" + id + " doesn't exist");
            return;
        }

        apartmentService.deleteById(id);

        if (!apartmentService.existsById(id)){
            System.out.println("Apartment with id=" + id + " successfully deleted");
        }else {
            System.out.println("Error: apartment wasn't deleted");
        }
    }
}
