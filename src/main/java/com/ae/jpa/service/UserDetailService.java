package com.ae.jpa.service;

import com.ae.jpa.model.dao.UserDao;
import com.ae.jpa.model.entity.RoleEntity;
import com.ae.jpa.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailService")
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDao dao;

    private Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user =  dao.findByUsername(s);

        if (user == null) {
            logger.error("Error login: No existe el usuario: " + s);
            throw new UsernameNotFoundException("User no existe");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

        for (RoleEntity role: user.getRoles()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (grantedAuthorityList == null) {
            logger.error("User no tiene roles");
            throw new UsernameNotFoundException("User no tiene roles");
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                grantedAuthorityList
                );
    }
}
