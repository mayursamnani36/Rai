package com.gamechanger.Rai_server.implementation;

import com.gamechanger.Rai_server.entity.TaskEntity;
import com.gamechanger.Rai_server.repository.TaskRepository;
import com.gamechanger.Rai_server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Long> getTasksByUserId(Long id) {
        return taskRepository.getTasksByUserId(id);
    }
}
