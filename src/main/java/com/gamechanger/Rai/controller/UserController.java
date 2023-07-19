package com.gamechanger.Rai.controller;

import com.gamechanger.Rai.entity.UserEntity;
import com.gamechanger.Rai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/setUser")
    public String setUser(@RequestBody UserEntity user){
        userService.saveUser(user);
        return "User Saved Successfully";
    }
}
