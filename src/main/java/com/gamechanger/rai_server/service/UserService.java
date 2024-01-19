package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.UserEntity;

public interface UserService {
    void createUser(UserEntity user);

    UserEntity findUserById(Long id);
}
