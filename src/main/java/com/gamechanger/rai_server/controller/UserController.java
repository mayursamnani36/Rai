package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity user){
        userService.createUser(user);
        return "User Saved Successfully with username " + user.getUserName();
    }
}
