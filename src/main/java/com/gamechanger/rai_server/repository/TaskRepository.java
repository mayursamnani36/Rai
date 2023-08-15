package com.gamechanger.rai_server.repository;

import com.gamechanger.rai_server.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query(value = "SELECT * FROM task_entity WHERE assignee = :userId", nativeQuery = true)
    List<TaskEntity> getTasksByUserId(Long userId);
    @Query(value = "SELECT * FROM task_entity WHERE tag = :tag", nativeQuery = true)
    List<TaskEntity> getTasksByTag(String tag);
    @Query(value = "SELECT * FROM task_entity WHERE title LIKE CONCAT('%', :title, '%')", nativeQuery = true)
    List<TaskEntity> searchTasksByTitle(String title);

}
