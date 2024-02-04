package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void createUser(UserEntity user);

    UserEntity findUserById(Long id);

    UserDetails loadUserByUsername(String username);
}
