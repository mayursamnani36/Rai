package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.service.CommentService;
import com.gamechanger.rai_server.utils.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Slf4j
@RestController
public class CommentController {

    private final CommentService commentService;
    private final RequestValidator requestValidator;

    @Autowired
    public CommentController(CommentService commentService, RequestValidator requestValidator) {
        this.commentService = commentService;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/addComment")
    public ResponseEntity<String> addComment(@RequestBody AddCommentDTO addCommentDTO){
        try {
            log.info("addCommentDTO: {}", addCommentDTO);
            if (!requestValidator.validateComment(addCommentDTO)) {
                return ResponseEntity.badRequest().body("Please enter a valid comment and make sure taskId and userId exists");
            }
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setComment(addCommentDTO.getComment());
            commentEntity.setUserId(addCommentDTO.getUserId());
            commentEntity.setTaskId(addCommentDTO.getTaskId());
            commentService.addComment(commentEntity);
            log.info("Comment added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment Added for task id " + addCommentDTO.getTaskId() + " by user with user id " + addCommentDTO.getUserId());
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
    @GetMapping("/getCommentsByTaskId")
    public ResponseEntity<List<CommentEntity>> getCommentsByTaskId(@RequestParam Long taskId){
        try {
            log.info("taskId: {}", taskId);
            List<CommentEntity> commentEntityList = commentService.getCommentsByTaskId(taskId);
            return ResponseEntity.ok(commentEntityList);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
