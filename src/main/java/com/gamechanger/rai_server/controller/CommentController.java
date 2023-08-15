package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/addComment")
    public String addComment(@RequestBody AddCommentDTO addCommentDTO){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(addCommentDTO.getComment());
        commentEntity.setUserId(addCommentDTO.getUserId());
        commentEntity.setTaskId(addCommentDTO.getTaskId());
        commentService.addComment(commentEntity);
        return "Comment Added for task id " + addCommentDTO.getTaskId() + " by user with user id " + addCommentDTO.getUserId();
    }
    @GetMapping("/getCommentsByTaskId")
    public List<CommentEntity> getCommentsByTaskId(@RequestParam Long taskId){
        return commentService.getCommentsByTaskId(taskId);
    }
}
