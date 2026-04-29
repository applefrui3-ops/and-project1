import controllers.ApartmentController;
import controllers.ClientController;
import models.*;
import repositories.ApartmentRepository;
import repositories.ClientRepository;
import repositories.InMemoryApartmentRepository;
import repositories.InMemoryClientRepository;
import services.ApartmentService;
import services.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

    private final int APARTMENT_BASE_PRICE = 50;
    private final Currency APARTMENT_BASE_CURRENCY = Currency.BYN;
    private final ReservationStatus APARTMENT_BASE_STATUS = ReservationStatus.FREE;

    public Application() {
        ApartmentRepository apartmentRepository = new InMemoryApartmentRepository(generateApartments());
        ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        ApartmentController apartmentController = new ApartmentController(apartmentService);

        ClientRepository clientRepository = new InMemoryClientRepository(generateClients());
        ClientService clientService = new ClientService(clientRepository);
        ClientController clientController = new ClientController(clientService);

        bindClientsAndApartments(apartmentService, clientService);
        Scanner scanner = new Scanner(System.in);
        String command;

        boolean appFlag = true;
        while (appFlag) {
            System.out.println("\nSelect a menu item: (enter the number of the selected menu item)\n");
            System.out.println("Main menu:");
            System.out.println("    1. apartments");
            System.out.println("    2. clients");
            System.out.println("    0. close application");
            System.out.print("\n->");
            command = scanner.next();


            if (command.equals("1")) {
                boolean flag = true;
                while (flag) {
                    System.out.println("\nSelect a menu item: (enter the number of the selected menu item)\n");
                    System.out.println("Apartments:");
                    System.out.println("    1. show list");
                    System.out.println("    2. edit");
                    System.out.println("    0. get back");
                    System.out.print("\n->");
                    command = scanner.next();
                    switch (command) {
                        case "1":
                            apartmentController.showList();
                            break;
                        case "2":
                            apartmentController.edit(scanner);
                            break;
                        case "0":
                            flag = false;
                            break;
                    }
                }

            } else if (command.equals("2")) {
                System.out.println("\nSelect a menu item: (enter the number of the selected menu item)\n");
                System.out.println("Clients:");
                System.out.println("    1. show list");
                System.out.println("    0. get back");
                System.out.print("\n->");
                command = scanner.next();
                switch (command) {
                    case "1":
                        clientController.showList();
                        break;
                    case "0":
                        break;
                }
            } else if (command.equals("0")) {
                appFlag = false;
            }
        }
    }


    private Apartment generateBaseApartment(long id) {
        return new Apartment(
                id,
                new Price(APARTMENT_BASE_PRICE, APARTMENT_BASE_CURRENCY),
                new ArrayList<>(),
                APARTMENT_BASE_STATUS
        );
    }

    private Map<Long, Apartment> generateApartments() {
        Map<Long, Apartment> map = new ConcurrentHashMap<>();
        for (long i = 1; i <= 40; i++) {
            map.put(i, generateBaseApartment(i));
        }
        return map;
    }

    private Map<Long, Client> generateClients() {
        Map<Long, Client> map = new ConcurrentHashMap<>();
            map.put((long)1, new Client(1, "Bob", null));
            map.put((long)2, new Client(2, "John", null));
            map.put((long)3, new Client(3, "Alex", null));
        return map;
    }

    private void bindClientsAndApartments(ApartmentService apartmentService, ClientService clientService){
        List<Apartment> apartments = apartmentService.findAll();
        List<Client> clients = clientService.findAll();
        for (Client client : clients){
            Apartment ap = apartments.get((int) client.getId());
            ap.setClients(List.of(client));
            ap.setReservationStatus(ReservationStatus.RESERVED);
            System.out.println(ap);
            System.out.println(client);
        }
    }
}
