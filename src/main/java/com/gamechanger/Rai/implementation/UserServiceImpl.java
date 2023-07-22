package com.gamechanger.Rai.implementation;

import com.gamechanger.Rai.entity.UserEntity;
import com.gamechanger.Rai.repository.UserRepository;
import com.gamechanger.Rai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public void createUser(UserEntity user) {userRepository.save(user);}
}
