package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    void addComment(CommentEntity commentEntity);

    List<CommentEntity> getCommentsByTaskId(Long taskId);
}
