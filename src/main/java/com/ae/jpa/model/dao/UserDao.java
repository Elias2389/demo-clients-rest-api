package com.ae.jpa.model.dao;

import com.ae.jpa.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserEntity, Long> {
    public UserEntity findByUsername(String username);
}
