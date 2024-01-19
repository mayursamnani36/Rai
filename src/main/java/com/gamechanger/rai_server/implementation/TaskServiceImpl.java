package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.repository.TaskRepository;
import com.gamechanger.rai_server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@CacheConfig(cacheNames = "tasks")
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(TaskEntity task) {
        taskRepository.save(task);
    }

    @Override
    @Cacheable(key = "#taskId")
    public TaskEntity getTaskByTaskId(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<TaskEntity> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByAssignee(userId);
    }

    @Override
    public List<TaskEntity> getTasksByTag(String tag) {
        return taskRepository.getTasksByTag(tag);
    }

    @Override
    public List<TaskEntity> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContaining(title);
    }
}
