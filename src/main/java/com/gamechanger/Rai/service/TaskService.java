package com.gamechanger.Rai.service;

import com.gamechanger.Rai.entity.TaskEntity;
import org.springframework.stereotype.Service;

public interface TaskService {
    public void saveTask(TaskEntity task);
}
