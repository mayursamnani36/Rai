package com.gamechanger.rai_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamechanger.rai_server.dto.AddBoardDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @Mock
    private UserService userService;

    @Mock
    private RequestValidator requestValidator;

    @InjectMocks
    private BoardController boardController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(boardController).build();
    }

    @Test
    void testCreateBoardSuccess() throws Exception {
        AddBoardDTO addBoardDTO = new AddBoardDTO("Board Title");

        mockMvc.perform(MockMvcRequestBuilders.post("/createBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Board Title board created."));

        verify(boardService, times(1)).saveBoard(any(BoardEntity.class));
    }

    @Test
    void testCreateBoardBadRequest() throws Exception {
        AddBoardDTO addBoardDTO = new AddBoardDTO(null); // Invalid title

        mockMvc.perform(MockMvcRequestBuilders.post("/createBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Title is required"));

        verify(boardService, never()).saveBoard(any(BoardEntity.class));
    }
    @Test
    void testCreateBoardException() throws Exception {
        AddBoardDTO addBoardDTO = new AddBoardDTO("Board Title");

        doThrow(new RuntimeException()).when(boardService).saveBoard(any(BoardEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/createBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(boardService, times(1)).saveBoard(any(BoardEntity.class));
    }
    @Test
    void testAddUserToBoardSuccess() throws Exception {
        AddUserToBoardDTO addUserToBoardDTO = new AddUserToBoardDTO("BoardTitle", 1L);

        when(requestValidator.validateUserToBoard(addUserToBoardDTO)).thenReturn(true);
        when(userService.findUserById(1L)).thenReturn(new UserEntity());
        when(boardService.getBoardByTitle("BoardTitle")).thenReturn(new BoardEntity());

        mockMvc.perform(MockMvcRequestBuilders.post("/addUserToBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addUserToBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("User Added to Board BoardTitle"));

        verify(boardService, times(1)).addUsersToBoard(any(UserEntity.class), any(BoardEntity.class));
    }

    @Test
    void testAddUserToBoardBadRequest() throws Exception {
        AddUserToBoardDTO addUserToBoardDTO = new AddUserToBoardDTO("BoardTitle", 1L);

        when(requestValidator.validateUserToBoard(addUserToBoardDTO)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/addUserToBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addUserToBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User/Board Doesn't exist in DB"));

        verify(boardService, never()).addUsersToBoard(any(UserEntity.class), any(BoardEntity.class));
    }

    @Test
    void testAddUserToBoardUserExistsInBoard() throws Exception {
        AddUserToBoardDTO addUserToBoardDTO = new AddUserToBoardDTO("BoardTitle", 1L);

        when(requestValidator.validateUserToBoard(addUserToBoardDTO)).thenReturn(true);
        when(userService.findUserById(1L)).thenReturn(new UserEntity());
        BoardEntity existingBoard = new BoardEntity();
        existingBoard.getUsers().add(new UserEntity());
        when(boardService.getBoardByTitle("BoardTitle")).thenReturn(existingBoard);

        mockMvc.perform(MockMvcRequestBuilders.post("/addUserToBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addUserToBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User with userId: 1 already exists in board: BoardTitle"));

        verify(boardService, never()).addUsersToBoard(any(UserEntity.class), any(BoardEntity.class));
    }

    @Test
    void testAddUserToBoardException() throws Exception {
        AddUserToBoardDTO addUserToBoardDTO = new AddUserToBoardDTO("BoardTitle", 1L);

        when(requestValidator.validateUserToBoard(addUserToBoardDTO)).thenReturn(true);
        when(userService.findUserById(1L)).thenReturn(new UserEntity());
        when(boardService.getBoardByTitle("BoardTitle")).thenReturn(new BoardEntity());
        doThrow(new RuntimeException("Simulating an exception during user addition to board")).when(boardService).addUsersToBoard(any(UserEntity.class), any(BoardEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/addUserToBoard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addUserToBoardDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Simulating an exception during user addition to board"));

        verify(boardService, times(1)).addUsersToBoard(any(UserEntity.class), any(BoardEntity.class));
    }

    @Test
    void testGetBoardsSuccess() throws Exception {
        List<BoardEntity> boardEntityList = Collections.singletonList(new BoardEntity("BoardTitle"));

        when(boardService.getBoards()).thenReturn(boardEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getBoards"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("BoardTitle"));

        verify(boardService, times(1)).getBoards();
    }

    @Test
    void testGetBoardsException() throws Exception {
        when(boardService.getBoards()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/getBoards"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(boardService, times(1)).getBoards();
    }
}
