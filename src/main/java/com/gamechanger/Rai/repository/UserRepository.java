package com.gamechanger.Rai.repository;

import com.gamechanger.Rai.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
