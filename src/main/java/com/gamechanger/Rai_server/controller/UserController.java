package com.gamechanger.Rai_server.controller;

import com.gamechanger.Rai_server.entity.TaskEntity;
import com.gamechanger.Rai_server.entity.UserEntity;
import com.gamechanger.Rai_server.service.TaskService;
import com.gamechanger.Rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity user){
        userService.createUser(user);
        return "User Saved Successfully";
    }
    @GetMapping("/getTasksByUserId")
    public List<TaskEntity> getTasksByUserId(@RequestParam Long userId){
       List<Long> taskList = taskService.getTasksByUserId(userId);
       List<TaskEntity> userTasks = new ArrayList<>();
       for(Long x : taskList){
           TaskEntity t = taskService.getTaskByTaskId(x);
           userTasks.add(t);
       }
       return userTasks;
    }
}
