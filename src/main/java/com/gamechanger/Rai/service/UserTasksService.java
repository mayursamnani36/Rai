package com.gamechanger.Rai.service;

import com.gamechanger.Rai.entity.UserTasksEntity;
import java.util.List;

public interface UserTasksService {
    void saveUserTasks(UserTasksEntity userTasksEntity);
    List<Long> getTasksById(Long id);
}
