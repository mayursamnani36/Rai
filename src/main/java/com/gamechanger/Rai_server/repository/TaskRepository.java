package com.gamechanger.Rai_server.repository;

import com.gamechanger.Rai_server.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query(value = "SELECT id FROM task_entity WHERE assignee = :id", nativeQuery = true)
    List<Long> getTasksByUserId(Long id);
}
