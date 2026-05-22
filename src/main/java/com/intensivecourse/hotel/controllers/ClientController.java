package com.intensivecourse.hotel.controllers;


import com.intensivecourse.hotel.models.Client;
import com.intensivecourse.hotel.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> findAll(){
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client){
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client client){
        client.setId(id);
        return clientService.save(client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        clientService.deleteById(id);
    }
}
