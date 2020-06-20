package com.ae.jpa.model.dao;

import com.ae.jpa.model.entity.ClientEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<ClientEntity> findAll() {
        return entityManager.createQuery("from ClientEntity").getResultList();
    }

    @Transactional
    @Override
    public void saveClient(ClientEntity client) {
        ClientEntity clientEntity = new ClientEntity();

        if (client != null) {
            clientEntity.setName(client.getName());
            clientEntity.setLastName(client.getLastName());
            clientEntity.setEmail(client.getEmail());
            clientEntity.setCreateAt(new Date());

            entityManager.persist(clientEntity);
        }

    }


}
