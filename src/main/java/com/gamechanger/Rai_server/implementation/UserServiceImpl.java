package com.gamechanger.Rai_server.implementation;

import com.gamechanger.Rai_server.entity.UserEntity;
import com.gamechanger.Rai_server.repository.UserRepository;
import com.gamechanger.Rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public void createUser(UserEntity user) {userRepository.save(user);}
}
