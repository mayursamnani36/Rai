package com.gamechanger.Rai_server.controller;

import com.gamechanger.Rai_server.entity.TaskEntity;
import com.gamechanger.Rai_server.entity.UserTasksEntity;
import com.gamechanger.Rai_server.service.TaskService;
import com.gamechanger.Rai_server.service.UserTasksService;
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
        if(task.getId()==null){
            taskService.createTask(task);
            UserTasksEntity userTasksEntity = new UserTasksEntity(task.getAssignee(), task.getId(), task.getAssignee()+task.getId());
            userTasksService.saveUserTasks(userTasksEntity);
            return "Task has been created with title "+ task.getTitle() + " for user with id " + task.getAssignee();
        }
        else {
            taskService.createTask(task);
            return "Task updated";
        }
    }
    @GetMapping("/getTaskByTaskId")
    public TaskEntity getTaskByTaskId(@RequestParam Long taskId){
        return taskService.getTaskByTaskId(taskId);
    }
}
