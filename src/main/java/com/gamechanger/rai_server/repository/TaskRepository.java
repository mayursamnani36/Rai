package com.gamechanger.rai_server.repository;

import com.gamechanger.rai_server.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> getTasksByAssignee(Long userId);
    List<TaskEntity> getTasksByTag(String tag);
    List<TaskEntity> findByTitleContaining(String title);

}
