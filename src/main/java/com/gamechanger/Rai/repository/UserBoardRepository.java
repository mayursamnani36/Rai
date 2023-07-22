package com.gamechanger.Rai.repository;

import com.gamechanger.Rai.entity.UserBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoardEntity, Long> {
}
