package com.gamechanger.Rai_server.service;

import com.gamechanger.Rai_server.entity.UserTasksEntity;
import java.util.List;

public interface UserTasksService {
    void saveUserTasks(UserTasksEntity userTasksEntity);
    List<Long> getTasksByUserId(Long id);
}
