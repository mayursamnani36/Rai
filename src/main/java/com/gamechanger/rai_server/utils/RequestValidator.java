package com.gamechanger.rai_server.utils;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.TaskService;
import com.gamechanger.rai_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RequestValidator {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    public boolean validateUser(UserEntity user){
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
}
