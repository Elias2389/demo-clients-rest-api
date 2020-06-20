package com.ae.jpa.controller;

import com.ae.jpa.model.dao.ClientDao;
import com.ae.jpa.model.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientDao dao;

    @GetMapping("/client")
    public List<ClientEntity> getAllClients() {
        return dao.findAll();
    }

    @PostMapping("/client")
    public String saveClient(@RequestBody ClientEntity clientEntity) {
        if (clientEntity != null) {
            dao.saveClient(clientEntity);
        }
        return "Listo";
    }
}
