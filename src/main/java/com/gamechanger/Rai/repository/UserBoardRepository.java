package com.gamechanger.Rai.repository;

import com.gamechanger.Rai.entity.UserBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserBoardRepository extends JpaRepository<UserBoardEntity, Long> {
}
