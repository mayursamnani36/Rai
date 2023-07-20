package com.gamechanger.Rai.implementation;

import com.gamechanger.Rai.entity.UserTasksEntity;
import com.gamechanger.Rai.repository.UserTasksRepository;
import com.gamechanger.Rai.service.UserTasksService;
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
    public List<Long> getTasksById(Long id) {
        return userTasksRepository.getTasksById(id);
    }

}
