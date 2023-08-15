package com.gamechanger.rai_server.repository;

import com.gamechanger.rai_server.entity.UserBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoardEntity, Long> {
}
