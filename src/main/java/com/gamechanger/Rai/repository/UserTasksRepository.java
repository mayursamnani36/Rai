package com.gamechanger.Rai.repository;

import com.gamechanger.Rai.entity.UserTasksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTasksRepository extends JpaRepository<UserTasksEntity, Long> {
    @Query(value = "SELECT task_id FROM user_tasks_entity WHERE user_id = :id", nativeQuery = true)
    List<Long> getTasksById(Long id);
}
