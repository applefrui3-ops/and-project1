import config.AppConfig;
import controllers.ApartmentController;
import controllers.ClientController;
import controllers.MenuController;
import models.*;
import repositories.*;
import services.ApartmentService;
import services.ClientService;
import util.DataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

    private final String SERIALIZED_APARTMENTS_PATH;
    private final String SERIALIZED_CLIENTS_PATH;
    private final int APARTMENT_BASE_PRICE;
    private final Currency APARTMENT_BASE_CURRENCY;
    private final ReservationStatus APARTMENT_BASE_STATUS;
    private final boolean CHANGE_APARTMENT_STATUS_ENABLED;

    public Application() {
        AppConfig appConfig = new AppConfig();

        SERIALIZED_APARTMENTS_PATH = appConfig.getProperty("serialized.apartment.path");
        SERIALIZED_CLIENTS_PATH = appConfig.getProperty("serialized.client.path");
        APARTMENT_BASE_PRICE = appConfig.getIntProperty("apartment.base.price");
        APARTMENT_BASE_CURRENCY = appConfig.getEnumProperty("apartment.base.currency", Currency.class);
        APARTMENT_BASE_STATUS = appConfig.getEnumProperty("apartment.base.status", ReservationStatus.class);
        CHANGE_APARTMENT_STATUS_ENABLED = appConfig.getBoolProperty("apartment.status.change");

        DataGenerator dataGenerator = new DataGenerator(APARTMENT_BASE_PRICE, APARTMENT_BASE_CURRENCY, APARTMENT_BASE_STATUS);

        ClientRepository clientRepository = new JsonClientRepository(SERIALIZED_CLIENTS_PATH);
        ClientService clientService = new ClientService(clientRepository);
        ClientController clientController = new ClientController(clientService);

        ApartmentRepository apartmentRepository = new JsonApartmentRepository(SERIALIZED_APARTMENTS_PATH);
        ApartmentService apartmentService = new ApartmentService(apartmentRepository);
        ApartmentController apartmentController = new ApartmentController(
                apartmentService,
                clientService,
                APARTMENT_BASE_PRICE,
                APARTMENT_BASE_CURRENCY,
                APARTMENT_BASE_STATUS
        );


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
                            apartmentController.add(scanner);
                            break;
                        case "3":
                            if(CHANGE_APARTMENT_STATUS_ENABLED){
                                apartmentController.editStatus(scanner);
                            }else {
                                System.out.println("\nStatus editing is disabled");
                            }
                            break;
                        case "4":
                            apartmentController.setClient(scanner);
                            break;
                        case "5":
                            apartmentController.delete(scanner);
                            break;
                        case "0":
                            flag = false;
                            break;
                    }
                }
            } else if (command.equals("2")) {
                boolean flag = true;
                while (flag) {
                    menuController.showClientMenu();
                    command = scanner.next();
                    switch (command) {
                        case "1":
                            clientController.showList();
                            break;
                        case "2":
                            clientController.add(scanner);
                            break;
                        case "3":
                            clientController.edit(scanner);
                            break;
                        case "4":
                            clientController.delete(scanner);
                            break;
                        case "0":
                            flag = false;
                            break;
                    }
                }
            } else if (command.equals("0")) {
                appFlag = false;
            }
        }
    }
}
