package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;

import java.util.List;

public interface UserService {
    void createUser(UserEntity user);

    UserEntity findUserByUserName(String userName);

    UserEntity findUserById(Long id);
}
