package com.gamechanger.Rai_server.service;

import com.gamechanger.Rai_server.entity.TaskEntity;

public interface TaskService {
    void createTask(TaskEntity task);
    TaskEntity getTaskByTaskId(Long taskId);
}
