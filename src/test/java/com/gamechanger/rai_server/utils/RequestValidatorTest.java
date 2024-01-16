package com.gamechanger.rai_server.utils;

import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.TaskService;
import com.gamechanger.rai_server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestValidatorTest {

    @Mock
    private UserService userService;

    @Mock
    private TaskService taskService;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private RequestValidator requestValidator;

    @Test
    public void testValidateUserValid() {
        UserEntity user = new UserEntity();
        user.setUserName("validUser");
        user.setPassword("validPassword");

        assertTrue(requestValidator.validateUser(user));
    }

    @Test
    public void testValidateUserInvalid() {
        UserEntity user = new UserEntity();

        assertFalse(requestValidator.validateUser(user));
    }

    @Test
    public void testValidateCommentValid() {
        AddCommentDTO addCommentDTO = new AddCommentDTO();
        addCommentDTO.setComment("Valid Comment");
        addCommentDTO.setUserId(1L);
        addCommentDTO.setTaskId(1L);

        when(userService.findUserById(1L)).thenReturn(new UserEntity());
        when(taskService.getTaskByTaskId(1L)).thenReturn(new TaskEntity());

        assertTrue(requestValidator.validateComment(addCommentDTO));
    }

    @Test
    public void testValidateCommentInvalid() {
        AddCommentDTO addCommentDTO = new AddCommentDTO();

        assertFalse(requestValidator.validateComment(addCommentDTO));
    }

    @Test
    public void testValidateTaskValid() {
        TaskEntity task = new TaskEntity();
        task.setTitle("Valid Title");
        task.setDescription("Valid Description");
        task.setAssignee(1L);
        task.setState("Valid State");
        task.setPriority("Valid Priority");
        task.setTag("Valid Tag");

        when(userService.findUserById(1L)).thenReturn(new UserEntity());

        assertTrue(requestValidator.validateTask(task));
    }

    @Test
    public void testValidateTaskInvalid() {
        TaskEntity task = new TaskEntity();

        assertFalse(requestValidator.validateTask(task));
    }

    @Test
    public void testValidateUserToBoardValid() {
        AddUserToBoardDTO addUserToBoardDTO = new AddUserToBoardDTO();
        addUserToBoardDTO.setBoard("Valid Board");
        addUserToBoardDTO.setUserId(1L);

        when(boardService.getBoardByTitle("Valid Board")).thenReturn(new BoardEntity());
        when(userService.findUserById(1L)).thenReturn(new UserEntity());

        assertTrue(requestValidator.validateUserToBoard(addUserToBoardDTO));
    }

    @Test
    public void testValidateUserToBoardInvalid() {
        AddUserToBoardDTO addUserToBoardDTO = new AddUserToBoardDTO();

        assertFalse(requestValidator.validateUserToBoard(addUserToBoardDTO));
    }
}
