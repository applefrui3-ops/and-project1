package util;

import models.*;
import services.ApartmentService;
import services.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataGenerator {
    private final int APARTMENT_BASE_PRICE;
    private final Currency APARTMENT_BASE_CURRENCY;
    private final ReservationStatus APARTMENT_BASE_STATUS;

    public DataGenerator(int apartmentBasePrice, Currency apartmentBaseCurrency, ReservationStatus apartmentBaseStatus){
        this.APARTMENT_BASE_PRICE = apartmentBasePrice;
        this.APARTMENT_BASE_CURRENCY = apartmentBaseCurrency;
        this.APARTMENT_BASE_STATUS = apartmentBaseStatus;
    }



    public Apartment generateBaseApartment(long id) {
        return new Apartment(
                id,
                new Price(APARTMENT_BASE_PRICE, APARTMENT_BASE_CURRENCY),
                new ArrayList<>(),
                APARTMENT_BASE_STATUS
        );
    }

    public Map<Long, Apartment> generateApartments() {
        Map<Long, Apartment> map = new ConcurrentHashMap<>();
        for (long i = 1; i <= 40; i++) {
            map.put(i, generateBaseApartment(i));
        }
        return map;
    }

    public Map<Long, Client> generateClients() {
        Map<Long, Client> map = new ConcurrentHashMap<>();
        map.put((long)1, new Client(1, "Bob"));
        map.put((long)2, new Client(2, "John"));
        map.put((long)3, new Client(3, "Alex"));
        return map;
    }

    public void bindClientsAndApartments(ApartmentService apartmentService, ClientService clientService){
        List<Apartment> apartments = apartmentService.findAll();
        List<Client> clients = clientService.findAll();
        for (Client client : clients){
            Apartment ap = apartments.get((int) client.getId());
            ap.setClients(List.of(client));
            ap.setReservationStatus(ReservationStatus.RESERVED);
        }
    }
}
