package com.gamechanger.rai_server.utils;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.TaskService;
import com.gamechanger.rai_server.service.UserService;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestValidator {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private BoardService boardService;

    public boolean validateUser(UserEntity user){
        // Unique username check is done in UserEntity class
        String userName = user.getUserName();
        String password = user.getPassword();
        if(StringUtils.isEmptyOrWhitespaceOnly(password) || password.length() < 8){
            log.error("Password is null or has size less than 8");
            return false;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(userName) || userName.length()>10 || userName.length()<3){
            log.error("Username is either null doesn't satisfy length constraints");
            return false;
        }
        return true;
    }

    public boolean validateComment(AddCommentDTO addCommentDTO) {
        String comment = addCommentDTO.getComment();
        Long userId = addCommentDTO.getUserId();
        Long taskId = addCommentDTO.getTaskId();
        UserEntity dbUser = userService.findUserById(userId);
        TaskEntity dbTask = taskService.getTaskByTaskId(taskId);

        if(StringUtils.isEmptyOrWhitespaceOnly(comment)){
            log.error("Comment is null or empty");
            return false;
        }
        if(dbTask==null){
            log.error("Task doesn't exists with taskId: {}", taskId);
            return false;
        }
        if(dbUser==null){
            log.error("User doesn't exists with userId: {}", userId);
            return false;
        }
        return true;
    }

    public boolean validateTask(TaskEntity task) {
        String title = task.getTitle();
        String description = task.getDescription();
        Long assignee = task.getAssignee();
        String state = task.getState();
        String priority = task.getPriority();
        String tag = task.getTag();
        UserEntity dbUser = userService.findUserById(assignee);
        if(StringUtils.isEmptyOrWhitespaceOnly(title)){
            log.error("Title is null or empty");
            return false;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(description)){
            log.error("Description is null or empty");
            return false;
        }
        if(dbUser==null){
            log.error("User does not exist with userId: {}", assignee);
            return false;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(state)){
            log.error("State is null or empty");
            return false;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(priority)){
            log.error("Priority is null or empty");
            return false;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(tag)){
            log.error("Tag is null or empty");
            return false;
        }
        return true;
    }

    public boolean validateUserToBoard(AddUserToBoardDTO body) {
        String board = body.getBoard();
        Long userId = body.getUserId();
        if(boardService.getBoardByTitle(board)==null){
            log.error("Board with title: {} does not exist", board);
            return false;
        }
        UserEntity dbUser = userService.findUserById(userId);
        if(dbUser==null){
            log.error("User does not exist with userId: {}", userId);
            return false;
        }
        return true;
    }
}
