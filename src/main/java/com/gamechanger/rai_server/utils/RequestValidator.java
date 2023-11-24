package com.gamechanger.rai_server.utils;

import com.gamechanger.rai_server.entity.UserEntity;

public final class RequestValidator {
    public static boolean validateUser(UserEntity user){
        String userName = user.getUserName();
        String password = user.getPassword();
        return userName.length() >= 4 && userName.length() <= 10 && password.length() >= 8;
    }
}
