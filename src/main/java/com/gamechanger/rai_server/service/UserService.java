package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.utils.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void createUser(UserEntity user);

    UserEntity findUserById(Long id);

    UserInfoDetails loadUserByUsername(String username);
}
