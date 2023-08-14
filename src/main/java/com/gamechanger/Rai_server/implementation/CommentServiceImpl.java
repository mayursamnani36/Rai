package com.gamechanger.Rai_server.implementation;

import com.gamechanger.Rai_server.entity.CommentEntity;
import com.gamechanger.Rai_server.repository.CommentRepository;
import com.gamechanger.Rai_server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public void addComment(CommentEntity commentEntity) {
        commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentEntity> getCommentsByTaskId(Long taskId) {
        List<CommentEntity> comments = commentRepository.getCommentsByTaskId(taskId);
        return comments;
    }
}
