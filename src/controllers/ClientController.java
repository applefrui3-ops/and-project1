package controllers;

import models.Apartment;
import models.Client;
import services.ClientService;

import java.util.List;

public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    public void showList(){
        List<Client> clients = clientService.findAll();
        System.out.println("-------");
        for (Client client : clients) {
            System.out.println("Id: " + client.getId());
            System.out.println("Name: " + client.getName());
            System.out.println("-------");
        }
        System.out.println("\n");
    }
}
