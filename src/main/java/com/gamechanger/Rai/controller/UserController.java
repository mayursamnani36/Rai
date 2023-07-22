package com.gamechanger.Rai.controller;

import com.gamechanger.Rai.entity.TaskEntity;
import com.gamechanger.Rai.entity.UserEntity;
import com.gamechanger.Rai.service.TaskService;
import com.gamechanger.Rai.service.UserService;
import com.gamechanger.Rai.service.UserTasksService;
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
    @Autowired
    UserTasksService userTasksService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity user){
        userService.createUser(user);
        return "User Saved Successfully";
    }
    @GetMapping("/getTasksByUserId")
    public List<TaskEntity> getTasksByUserId(@RequestParam Long userId){
       List<Long> taskList = userTasksService.getTasksByUserId(userId);
       List<TaskEntity> userTasks = new ArrayList<>();
       for(Long x : taskList){
           TaskEntity t = taskService.getTaskByTaskId(x);
           userTasks.add(t);
       }
       return userTasks;
    }
}
