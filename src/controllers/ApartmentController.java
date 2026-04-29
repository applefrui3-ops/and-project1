package controllers;

import models.Apartment;
import models.ReservationStatus;
import services.ApartmentService;

import java.util.*;

public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }


    public void showList() {
        List<Apartment> apartments = apartmentService.findAll();
        System.out.println("-------");
        for (Apartment apartment : apartments) {
            System.out.println("Number: " + apartment.getId());
            System.out.println("Price: " + apartment.getPrice().getValue()
                    + " " + apartment.getPrice().getCurrency());
            System.out.println("Status: " + apartment.getReservationStatus());
            System.out.println("Residents: " + apartment.getClients());
            System.out.println("-------");
        }
        System.out.println("\n");
    }

    public void edit(Scanner scanner) {
        System.out.println("Insert the apartment number");
        String roomNumber = scanner.next();
        Apartment apartment = apartmentService.findById(Long.parseLong(roomNumber));

        System.out.println("Choose appropriate status for this apartment");
        System.out.println("1. FREE");
        System.out.println("2. RESERVED");
        System.out.println("3. OCCUPIED");
        System.out.print("\n->");
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
}
