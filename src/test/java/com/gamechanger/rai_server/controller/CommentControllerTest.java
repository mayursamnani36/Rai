package com.gamechanger.rai_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamechanger.rai_server.dto.AddCommentDTO;
import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.service.CommentService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.any;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private RequestValidator requestValidator;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(commentController).build();
    }

    @Test
    public void testAddCommentSuccess() throws Exception {
        AddCommentDTO addCommentDTO = new AddCommentDTO("Valid comment", 1L, 2L);

        when(requestValidator.validateComment(addCommentDTO)).thenReturn(true);
        doNothing().when(commentService).addComment(any(CommentEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/addComment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addCommentDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Comment Added for task id 2 by user with user id 1"));

        verify(requestValidator, times(1)).validateComment(addCommentDTO);
        verify(commentService, times(1)).addComment(any(CommentEntity.class));
    }

    @Test
    public void testAddCommentBadRequest() throws Exception {
        AddCommentDTO addCommentDTO = new AddCommentDTO("Short", 1L, 2L);

        when(requestValidator.validateComment(addCommentDTO)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/addComment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addCommentDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Please enter a valid comment and make sure taskId and userId exists"));

        verify(requestValidator, times(1)).validateComment(addCommentDTO);
        verify(commentService, never()).addComment(any(CommentEntity.class));
    }

    @Test
    public void testAddCommentException() throws Exception {
        AddCommentDTO addCommentDTO = new AddCommentDTO("Valid comment", 1L, 2L);

        when(requestValidator.validateComment(addCommentDTO)).thenReturn(true);
        doThrow(new RuntimeException("Simulating an exception during comment addition")).when(commentService).addComment(any(CommentEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/addComment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addCommentDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Simulating an exception during comment addition"));

        verify(requestValidator, times(1)).validateComment(addCommentDTO);
        verify(commentService, times(1)).addComment(any(CommentEntity.class));
    }
    @Test
    public void testGetCommentsByTaskIdSuccess() throws Exception {
        Long taskId = 1L;

        when(commentService.getCommentsByTaskId(taskId)).thenReturn(Collections.singletonList(new CommentEntity("Valid comment", 1L, taskId)));

        mockMvc.perform(MockMvcRequestBuilders.get("/getCommentsByTaskId")
                        .param("taskId", taskId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value("Valid comment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(taskId));
        verify(commentService, times(1)).getCommentsByTaskId(taskId);
    }

    @Test
    public void testGetCommentsByTaskIdException() throws Exception {
        Long taskId = 1L;

        when(commentService.getCommentsByTaskId(taskId)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/getCommentsByTaskId")
                        .param("taskId", taskId.toString()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        verify(commentService, times(1)).getCommentsByTaskId(taskId);
    }
}
