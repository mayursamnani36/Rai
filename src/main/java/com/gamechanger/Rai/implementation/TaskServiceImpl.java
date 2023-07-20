package com.gamechanger.Rai.implementation;

import com.gamechanger.Rai.entity.TaskEntity;
import com.gamechanger.Rai.repository.TaskRepository;
import com.gamechanger.Rai.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Override
    public void createTask(TaskEntity task) {
        taskRepository.save(task);
    }

    @Override
    public TaskEntity getTaskByTaskId(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
}
