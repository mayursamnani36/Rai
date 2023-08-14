package com.gamechanger.Rai_server.service;

import com.gamechanger.Rai_server.entity.TaskEntity;

import java.util.List;

public interface TaskService {
    void createTask(TaskEntity task);
    TaskEntity getTaskByTaskId(Long taskId);
    List<Long> getTasksByUserId(Long userId);
}
