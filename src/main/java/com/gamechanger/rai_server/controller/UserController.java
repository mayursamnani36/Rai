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

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity user){
        try{
            if(!RequestValidator.validateUser(user)){
                throw new Exception("Please Enter a valid Request with username size between 4 to 10 and password size greater than 8");
            }
            UserEntity dbUser = userService.findUserByUsername(user.getUserName());
            if(dbUser == null){
                userService.createUser(user);
                return "User Saved Successfully with username " + user.getUserName();
            }
            else{
                throw new Exception("User with username " + user.getUserName() + " already exists");
            }
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
}
