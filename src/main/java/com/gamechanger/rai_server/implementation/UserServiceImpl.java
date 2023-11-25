package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.UserRepository;
import com.gamechanger.rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserEntity findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
