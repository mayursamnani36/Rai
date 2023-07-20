package com.gamechanger.Rai.controller;

import com.gamechanger.Rai.entity.UserEntity;
import com.gamechanger.Rai.service.UserService;
import com.gamechanger.Rai.service.UserTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserTasksService userTasksService;

    @PostMapping("/setUser")
    public String setUser(@RequestBody UserEntity user){
        userService.saveUser(user);
        return "User Saved Successfully";
    }
    @GetMapping("/getTasksById")
    public List<Long> getTasksById(@RequestParam Long id){
       return  userTasksService.getTasksById(id);
    }
}
