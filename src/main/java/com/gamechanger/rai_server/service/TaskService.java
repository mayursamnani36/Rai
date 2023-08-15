package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.TaskEntity;

import java.util.List;

public interface TaskService {
    void createTask(TaskEntity task);
    TaskEntity getTaskByTaskId(Long taskId);
    List<TaskEntity> getTasksByUserId(Long userId);

    List<TaskEntity> getTasksByTag(String tag);

    List<TaskEntity> searchTasksByTitle(String title);
}
