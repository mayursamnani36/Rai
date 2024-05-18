package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    void addComment(AddCommentDTO addCommentDTO);

    List<CommentEntity> getCommentsByTaskId(Long taskId);
}
