package com.gamechanger.Rai_server.repository;

import com.gamechanger.Rai_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
