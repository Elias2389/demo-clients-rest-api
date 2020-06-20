package com.ae.jpa.service;

import com.ae.jpa.model.dao.ClientDao;
import com.ae.jpa.model.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao dao;

    @Override
    public List<ClientEntity> findAll() {
        return dao.findAll();
    }

    @Override
    public void saveClient(ClientEntity client) {
        if (client != null) {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setName(client.getName());
            clientEntity.setLastName(client.getLastName());
            clientEntity.setEmail(client.getEmail());
            clientEntity.setCreateAt(new Date());
            dao.saveClient(clientEntity);
        }
    }

    @Override
    public void updateClient(ClientEntity client) {
        if (client != null) {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setName(client.getName());
            clientEntity.setLastName(client.getLastName());
            clientEntity.setEmail(client.getEmail());
            clientEntity.setCreateAt(new Date());
            clientEntity.setId(client.getId());
            dao.updateClient(clientEntity);
        }
    }

    @Override
    public ClientEntity findClient(Long id) {
        return dao.findClient(id);
    }

    @Override
    public void deleteClient(Long id) {
        dao.deleteClient(id);
    }
}
