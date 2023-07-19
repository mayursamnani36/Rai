package com.gamechanger.Rai.repository;

import com.gamechanger.Rai.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
