package com.gamechanger.Rai.repository;

import com.gamechanger.Rai.entity.UserTasksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTasksRepository extends JpaRepository<UserTasksEntity, Long> {

}
