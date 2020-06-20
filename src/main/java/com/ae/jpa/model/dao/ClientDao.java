package com.ae.jpa.model.dao;

import com.ae.jpa.model.entity.ClientEntity;

import java.util.List;

public interface ClientDao {

    public List<ClientEntity> findAll();

    public void saveClient(final ClientEntity client);
}
