package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.repository.CommentRepository;
import com.gamechanger.rai_server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, RedisTemplate<String, Object> redisTemplate) {
        this.commentRepository = commentRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addComment(AddCommentDTO addCommentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(addCommentDTO.getComment());
        commentEntity.setUserId(addCommentDTO.getUserId());
        commentEntity.setTaskId(addCommentDTO.getTaskId());
        redisTemplate.opsForHash().delete("COMMENTS_BY_TASKID", String.valueOf(addCommentDTO.getTaskId()));
        commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentEntity> getCommentsByTaskId(Long taskId) {
        List<CommentEntity> commentList = (List<CommentEntity>) redisTemplate.opsForHash().get("COMMENTS_BY_TASKID", String.valueOf(taskId));
        if(commentList==null){
            commentList = commentRepository.getCommentsByTaskId(taskId);
            redisTemplate.opsForHash().put("COMMENTS_BY_TASKID", String.valueOf(taskId), commentList);
        }
        return commentList;
    }
}
