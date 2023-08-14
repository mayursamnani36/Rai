package com.gamechanger.Rai_server.controller;

import com.gamechanger.Rai_server.entity.TaskEntity;
import com.gamechanger.Rai_server.entity.UserEntity;
import com.gamechanger.Rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity user){
        userService.createUser(user);
        return "User Saved Successfully";
    }
}
