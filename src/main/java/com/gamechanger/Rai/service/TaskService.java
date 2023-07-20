package com.gamechanger.Rai.service;

import com.gamechanger.Rai.entity.TaskEntity;

public interface TaskService {
    void createTask(TaskEntity task);

    TaskEntity getTaskByTaskId(Long taskId);
}
