package com.gamechanger.Rai.controller;

import com.gamechanger.Rai.entity.TaskEntity;
import com.gamechanger.Rai.entity.UserTasksEntity;
import com.gamechanger.Rai.service.TaskService;
import com.gamechanger.Rai.service.UserTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserTasksService userTasksService;

    @PostMapping("/createTask")
    public String createTask(@RequestBody TaskEntity task){
        taskService.createTask(task);
        UserTasksEntity userTasksEntity = new UserTasksEntity(task.getAssignee(), task.getId(), task.getAssignee()+task.getId());
        userTasksService.saveUserTasks(userTasksEntity);
        return "Task has been created with title "+ task.getTitle() + " for user with id " + task.getAssignee();
    }
    @GetMapping("/getTaskByTaskId")
    public TaskEntity getTaskByTaskId(@RequestParam Long taskId){
        return taskService.getTaskByTaskId(taskId);
    }
}
