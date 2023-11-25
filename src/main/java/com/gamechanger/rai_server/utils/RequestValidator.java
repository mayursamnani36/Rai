package com.gamechanger.rai_server.utils;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.TaskService;
import com.gamechanger.rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
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
        return userName.length() >= 4 && userName.length() <= 10 && password.length() >= 8;
    }

    public boolean validateComment(AddCommentDTO addCommentDTO) {
        String comment = addCommentDTO.getComment();
        Long userId = addCommentDTO.getUserId();
        Long taskId = addCommentDTO.getTaskId();
        UserEntity dbUser = userService.findUserById(userId);
        TaskEntity dbTask = taskService.getTaskByTaskId(taskId);
        return !comment.isEmpty() && !(dbTask==null) && !(dbUser==null);
    }

    public boolean validateTask(TaskEntity task) {
        String title = task.getTitle();
        String description = task.getDescription();
        Long assignee = task.getAssignee();
        String state = task.getState();
        String priority = task.getPriority();
        String tag = task.getTag();
        UserEntity dbUser = userService.findUserById(assignee);
        return !title.isEmpty() && !description.isEmpty() && dbUser!=null && !state.isEmpty() && !priority.isEmpty() && !tag.isEmpty();
    }

    public boolean validateUserToBoard(AddUserToBoardDTO body) {
        String board = body.getBoard();
        List<Long> users = body.getUsers();
        if(boardService.getBoardByTitle(board)==null){return false;}
        for(Long id : users){
            UserEntity dbUser = userService.findUserById(id);
            if(dbUser==null){return false;}
        }
        return true;
    }
}
