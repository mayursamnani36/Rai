package com.gamechanger.Rai_server.implementation;

import com.gamechanger.Rai_server.entity.UserTasksEntity;
import com.gamechanger.Rai_server.repository.UserTasksRepository;
import com.gamechanger.Rai_server.service.UserTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTasksServiceImpl implements UserTasksService {

    @Autowired
    UserTasksRepository userTasksRepository;
    @Override
    public void saveUserTasks(UserTasksEntity userTasksEntity) {
        userTasksRepository.save(userTasksEntity);
    }

    @Override
    public List<Long> getTasksByUserId(Long id) {
        return userTasksRepository.getTasksById(id);
    }

}
