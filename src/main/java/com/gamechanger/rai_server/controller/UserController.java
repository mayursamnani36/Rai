package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestValidator requestValidator;

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity user){
        try{
            if(!requestValidator.validateUser(user)){
                throw new Exception("Please Enter a valid Request with username size between 4 to 10 and password size greater than 8");
            }
            userService.createUser(user);
            return "User Saved Successfully with username " + user.getUserName();
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
}
