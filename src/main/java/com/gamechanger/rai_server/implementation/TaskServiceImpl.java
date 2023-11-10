package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.repository.TaskRepository;
import com.gamechanger.rai_server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public void createTask(TaskEntity task) {
        taskRepository.save(task);
    }

    @Override
    public TaskEntity getTaskByTaskId(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<TaskEntity> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId);
    }

    @Override
    public List<TaskEntity> getTasksByTag(String tag) {
        return taskRepository.getTasksByTag(tag);
    }

    @Override
    public List<TaskEntity> searchTasksByTitle(String title) {
        return taskRepository.searchTasksByTitle(title);
    }
}
