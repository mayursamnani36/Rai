package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.service.CommentService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private RequestValidator requestValidator;

    @PostMapping("/addComment")
    public String addComment(@RequestBody AddCommentDTO addCommentDTO){
        try {
            if (!requestValidator.validateComment(addCommentDTO)) {
                throw new Exception("Please enter a valid comment and make sure taskId and userId exists");
            }
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setComment(addCommentDTO.getComment());
            commentEntity.setUserId(addCommentDTO.getUserId());
            commentEntity.setTaskId(addCommentDTO.getTaskId());
            commentService.addComment(commentEntity);
            return "Comment Added for task id " + addCommentDTO.getTaskId() + " by user with user id " + addCommentDTO.getUserId();
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
    @GetMapping("/getCommentsByTaskId")
    public List<CommentEntity> getCommentsByTaskId(@RequestParam Long taskId){
        return commentService.getCommentsByTaskId(taskId);
    }
}
