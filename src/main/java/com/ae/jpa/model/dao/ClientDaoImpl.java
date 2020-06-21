package com.ae.jpa.model.dao;

import com.ae.jpa.model.entity.ClientEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public void saveClient(final ClientEntity client) {
        entityManager.persist(client);
    }

    @Transactional
    @Override
    public void updateClient(final ClientEntity client) {
        if (client.getId() != null && client.getId() > 0) {
            entityManager.merge(client);
        } else {
            entityManager.persist(client);
        }
    }

    @Transactional
    @Override
    public ClientEntity findClient(final Long id) {
        return entityManager.find(ClientEntity.class, id);
    }

    @Transactional
    @Override
    public void deleteClient(final Long id) {
        entityManager.remove(findClient(id));
    }


}
