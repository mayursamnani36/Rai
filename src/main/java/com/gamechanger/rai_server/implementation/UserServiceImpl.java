package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.UserRepository;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.UserInfoDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserEntity user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try{
            UserEntity user = userRepository.findUserByUserName(username);
            if(user == null){
                log.error("UserService:loadUserByUsername Username not found: " + username);
                throw new UsernameNotFoundException("could not found user..!!");
            }
            log.info("UserService:loadUserByUsername User Authenticated Successfully..!!!");
            return new UserInfoDetails(user);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }
}
