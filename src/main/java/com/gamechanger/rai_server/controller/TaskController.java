package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.service.TaskService;
import com.gamechanger.rai_server.utils.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TaskController {

    private final TaskService taskService;
    private final RequestValidator requestValidator;

    @Autowired
    public TaskController(TaskService taskService, RequestValidator requestValidator) {
        this.taskService = taskService;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/createTask")
    public ResponseEntity<String> createTask(@RequestBody TaskEntity task){
        try{
            log.info("task: {}", task);
            if(!requestValidator.validateTask(task)) {
                return ResponseEntity.badRequest().body("Invalid Task");
            }
            Long currentId = task.getId();
            taskService.createTask(task);
            log.info("Task created/updated successfully");
            return currentId==null ? ResponseEntity.status(HttpStatus.CREATED).body("Task has been created with title "+ task.getTitle() + " for user with id " + task.getAssignee())
                    :ResponseEntity.status(HttpStatus.CREATED).body("Task updated");
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping("/getTaskByTaskId")
    public ResponseEntity<TaskEntity> getTaskByTaskId(@RequestParam Long taskId){
        try {
            log.info("taskId: {}", taskId);
            TaskEntity task = taskService.getTaskByTaskId(taskId);
            return ResponseEntity.ok(task);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/cloneTask")
    public ResponseEntity<String> cloneTask(@RequestParam Long taskId){
        try{
            log.info("taskId: {}", taskId);
            TaskEntity task = getTaskByTaskId(taskId).getBody();
            if(task==null){
                return ResponseEntity.badRequest().body("Task does not exist with taskId: "+taskId);
            }
            TaskEntity clonedTask = new TaskEntity(task);
            return createTask(clonedTask);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping("/getTasksByUserId")
    public ResponseEntity<List<TaskEntity>> getTasksByUserId(@RequestParam Long userId){
        try {
            log.info("userId: {}", userId);
            List<TaskEntity> taskEntityList = taskService.getTasksByUserId(userId);
            return ResponseEntity.ok(taskEntityList);
        }
        catch (Exception ex){
            log.info(ex.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/getTasksByTag")
    public ResponseEntity<List<TaskEntity>> getTasksByTag(@RequestParam String tag){
        try{
            log.info("tag: {}", tag);
            List<TaskEntity> taskEntityList = taskService.getTasksByTag(tag);
            return ResponseEntity.ok(taskEntityList);
        }
        catch (Exception ex){
            log.info(ex.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskEntity>> searchTasksByTitle(@RequestParam String title) {
        try {
            log.info("title: {}", title);
            List<TaskEntity> taskEntityList = taskService.searchTasksByTitle(title);
            return ResponseEntity.ok(taskEntityList);
        }
        catch (Exception ex){
            log.info(ex.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
