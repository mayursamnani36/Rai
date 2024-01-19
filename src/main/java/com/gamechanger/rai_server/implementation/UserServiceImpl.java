package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.UserRepository;
import com.gamechanger.rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
