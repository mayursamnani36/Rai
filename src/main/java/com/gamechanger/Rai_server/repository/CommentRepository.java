package com.gamechanger.Rai_server.repository;

import com.gamechanger.Rai_server.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value = "SELECT id,user_id,task_id,comment FROM comment_entity WHERE task_id = :taskId", nativeQuery = true)
    List<CommentEntity> getCommentsByTaskId(Long taskId);
}
