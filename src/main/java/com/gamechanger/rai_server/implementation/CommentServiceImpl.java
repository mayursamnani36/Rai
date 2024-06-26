package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.repository.CommentRepository;
import com.gamechanger.rai_server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(AddCommentDTO addCommentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(addCommentDTO.getComment());
        commentEntity.setUserId(addCommentDTO.getUserId());
        commentEntity.setTaskId(addCommentDTO.getTaskId());
        commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentEntity> getCommentsByTaskId(Long taskId) {
        return commentRepository.getCommentsByTaskId(taskId);
    }
}
