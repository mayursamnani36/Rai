package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.service.TaskService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RequestValidator requestValidator;

    @PostMapping("/createTask")
    public String createTask(@RequestBody TaskEntity task){
        try{
            if(!requestValidator.validateTask(task)) {
                throw new Exception("Invalid Task");
            }
            Long currentId = task.getId();
            taskService.createTask(task);
            return currentId==null ? "Task has been created with title "+ task.getTitle() + " for user with id " + task.getAssignee():"Task updated";
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
    @GetMapping("/getTaskByTaskId")
    public TaskEntity getTaskByTaskId(@RequestParam Long taskId){
        return taskService.getTaskByTaskId(taskId);
    }

    @PostMapping("/cloneTask")
    public String cloneTask(@RequestParam Long taskId){
        try{
            TaskEntity task = getTaskByTaskId(taskId);
            if(task==null){
                throw new Exception("Task does not exist");
            }
            TaskEntity clonedTask = new TaskEntity(task);
            return createTask(clonedTask);
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
    @GetMapping("/getTasksByUserId")
    public List<TaskEntity> getTasksByUserId(@RequestParam Long userId){
        return taskService.getTasksByUserId(userId);
    }
    @GetMapping("/getTasksByTag")
    public List<TaskEntity> getTasksByTag(@RequestParam String tag){
        return taskService.getTasksByTag(tag);
    }
    @GetMapping("/search")
    public List<TaskEntity> searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTasksByTitle(title);
    }
}
