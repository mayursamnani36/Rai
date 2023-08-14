package com.gamechanger.Rai_server.service;

import com.gamechanger.Rai_server.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    void addComment(CommentEntity commentEntity);

    List<CommentEntity> getCommentsByTaskId(Long taskId);
}
