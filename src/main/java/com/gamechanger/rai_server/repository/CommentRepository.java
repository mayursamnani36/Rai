package com.gamechanger.rai_server.repository;

import com.gamechanger.rai_server.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> getCommentsByTaskId(Long taskId);
}
