package legacy.controllers;


import com.intensivecourse.hotel.models.Client;
import com.intensivecourse.hotel.services.ClientService;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    private void printClient(Client client) {
        System.out.println("-------");
        System.out.println("Id: " + client.getId());
        System.out.println("Name: " + client.getName());
        System.out.println("-------");
    }

    public void showList() {
        clientService.findAll().stream()
                .sorted(Comparator.comparingLong(Client::getId))
                .forEach(this::printClient);
        System.out.println("\n");
    }

    public void add(Scanner scanner) {
        System.out.println("Insert client id:");
        System.out.print("->");
        long id = Long.parseLong(scanner.next());

        System.out.println("Insert client name:");
        System.out.print("->");
        String name = scanner.next();

        Client newClient = new Client(id, name);
        clientService.saveClient(newClient);

        Client client = clientService.findById(id);
        if (newClient.equals(client)) {
            System.out.println("New client is created: " + newClient.toString());
        } else {
            System.out.println("Error: client wasn't created");
        }
    }

    public void edit(Scanner scanner) {
        System.out.println("Insert client id:");
        System.out.print("->");
        long id = Long.parseLong(scanner.next());
        Client client = clientService.findById(id);

        System.out.println("Insert client name:");
        System.out.print("->");
        String name = scanner.next();
        client.setName(name);

        clientService.saveClient(client);
        System.out.println("Client is updated: " + client.toString());
    }

    public void delete(Scanner scanner) {
        System.out.println("Insert client id:");
        System.out.print("->");
        long id = Long.parseLong(scanner.next());

        if (!clientService.existsById(id)) {
            System.out.println("Client with id=" + id + " doesn't exist");
            return;
        }
        clientService.deleteById(id);

        if (!clientService.existsById(id)) {
            System.out.println("Client with id=" + id + " successfully deleted");
        } else {
            System.out.println("Error: client wasn't deleted");
        }
    }
}
