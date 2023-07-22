package com.gamechanger.Rai_server.repository;

import com.gamechanger.Rai_server.entity.UserBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoardEntity, Long> {
}
