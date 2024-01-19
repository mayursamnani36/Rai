package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;
    private final RequestValidator requestValidator;

    @Autowired
    public UserController(UserService userService, RequestValidator requestValidator) {
        this.userService = userService;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserEntity user){
        try{
            log.info("user: {}", user);
            if(!requestValidator.validateUser(user)){
                return ResponseEntity.badRequest().body("Please Enter a valid Request with username size between 4 to 10 and password size greater than 8");
            }
            userService.createUser(user);
            log.info("User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("User Saved Successfully with username " + user.getUserName());
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
