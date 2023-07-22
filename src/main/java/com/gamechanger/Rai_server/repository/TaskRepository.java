package com.gamechanger.Rai_server.repository;

import com.gamechanger.Rai_server.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
