package com.ae.jpa.controller;

import com.ae.jpa.model.entity.ClientEntity;
import com.ae.jpa.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("/client")
    public List<ClientEntity> getAllClients() {
        return service.findAll();
    }

    @PostMapping("/client")
    public String saveClient(@RequestBody ClientEntity clientEntity) {
        if (clientEntity != null) {
            ClientEntity client = new ClientEntity();
            client.setName(clientEntity.getName());
            client.setLastName(clientEntity.getLastName());
            client.setEmail(clientEntity.getEmail());
            client.setCreateAt(new Date());
            service.saveClient(client);
        }
        return "Listo";
    }

    @PutMapping("/client/{id}")
    public String updateClient(final @RequestBody ClientEntity clientEntity, final @PathVariable("id") Long id) {
        if (clientEntity != null) {
            ClientEntity client = new ClientEntity();
            client.setName(clientEntity.getName());
            client.setLastName(clientEntity.getLastName());
            client.setEmail(clientEntity.getEmail());
            client.setCreateAt(new Date());
            client.setId(id);
            service.updateClient(client);
        }
        return "Listo";
    }

    @GetMapping("/client/{id}")
    public ClientEntity getClient(final @PathVariable("id") Long id) {
        return service.findClient(id);
    }

    @DeleteMapping("/client/{id}")
    public String deleteClient(final @PathVariable("id") Long id) {
        service.deleteClient(id);
        return "Listo";
    }
}
