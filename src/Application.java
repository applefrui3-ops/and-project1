import controllers.ApartmentController;
import controllers.ClientController;
import controllers.MenuController;
import models.*;
import repositories.ApartmentRepository;
import repositories.ClientRepository;
import repositories.InMemoryApartmentRepository;
import repositories.InMemoryClientRepository;
import services.ApartmentService;
import services.ClientService;
import util.DataGenerator;

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
        DataGenerator dataGenerator = new DataGenerator(APARTMENT_BASE_PRICE, APARTMENT_BASE_CURRENCY, APARTMENT_BASE_STATUS);

        ApartmentRepository apartmentRepository = new InMemoryApartmentRepository(dataGenerator.generateApartments());
        ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        ApartmentController apartmentController = new ApartmentController(apartmentService);

        ClientRepository clientRepository = new InMemoryClientRepository(dataGenerator.generateClients());
        ClientService clientService = new ClientService(clientRepository);
        ClientController clientController = new ClientController(clientService);

        MenuController menuController = new MenuController();

        dataGenerator.bindClientsAndApartments(apartmentService, clientService);
        Scanner scanner = new Scanner(System.in);
        String command;

        boolean appFlag = true;
        while (appFlag) {
            menuController.showMainMenu();
            command = scanner.next();


            if (command.equals("1")) {
                boolean flag = true;
                while (flag) {
                    menuController.showApartmentMenu();
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
                menuController.showClientMenu();
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
}
