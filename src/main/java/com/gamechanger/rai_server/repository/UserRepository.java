package com.gamechanger.rai_server.repository;

import com.gamechanger.rai_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
