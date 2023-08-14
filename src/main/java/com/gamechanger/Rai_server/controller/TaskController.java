package com.gamechanger.Rai_server.controller;

import com.gamechanger.Rai_server.entity.TaskEntity;
import com.gamechanger.Rai_server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/createTask")
    public String createTask(@RequestBody TaskEntity task){
        Long currentId = task.getId();
        taskService.createTask(task);
        return currentId==null ? "Task has been created with title "+ task.getTitle() + " for user with id " + task.getAssignee():"Task updated";
    }
    @GetMapping("/getTaskByTaskId")
    public TaskEntity getTaskByTaskId(@RequestParam Long taskId){
        return taskService.getTaskByTaskId(taskId);
    }

    @PostMapping("/cloneTask/{taskId}")
    public String cloneTask(@PathVariable("taskId") Long taskId){
        TaskEntity task = getTaskByTaskId(taskId);
        TaskEntity clonedTask = new TaskEntity(task);
        return createTask(clonedTask);
    }
}
