package com.gamechanger.rai_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.service.TaskService;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private RequestValidator requestValidator;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = standaloneSetup(taskController).build();
    }

    @Test
    void testCreateTaskSuccess() throws Exception {
        TaskEntity taskEntity = new TaskEntity();

        when(requestValidator.validateTask(taskEntity)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(taskService, times(1)).createTask(taskEntity);
    }

    @Test
    void testCreateTaskBadRequest() throws Exception {
        TaskEntity taskEntity = new TaskEntity();

        when(requestValidator.validateTask(taskEntity)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskEntity)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(taskService, never()).createTask(taskEntity);
    }

    @Test
    void testGetTaskByTaskIdSuccess() throws Exception {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);

        when(taskService.getTaskByTaskId(1L)).thenReturn(taskEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/getTaskByTaskId?taskId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

        verify(taskService, times(1)).getTaskByTaskId(1L);
    }

    @Test
    void testGetTaskByTaskIdException() throws Exception {
        when(taskService.getTaskByTaskId(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/getTaskByTaskId?taskId=1"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(taskService, times(1)).getTaskByTaskId(1L);
    }

    @Test
    void testCloneTaskBadRequest() throws Exception {
        when(taskService.getTaskByTaskId(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/cloneTask?taskId=1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("Task does not exist with taskId: 1"));

        verify(taskService, never()).createTask(any(TaskEntity.class));
    }

    @Test
    void testCloneTaskException() throws Exception {
        when(taskService.getTaskByTaskId(1L)).thenReturn(new TaskEntity());
        when(taskController.createTask(any(TaskEntity.class))).thenThrow(new RuntimeException("Simulating an exception during task cloning"));

        mockMvc.perform(MockMvcRequestBuilders.post("/cloneTask?taskId=1"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(content().string("Simulating an exception during task cloning"));

        verify(taskService, times(1)).getTaskByTaskId(1L);
    }

    @Test
    void testGetTasksByUserIdSuccess() throws Exception {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        when(taskService.getTasksByUserId(1L)).thenReturn(Collections.singletonList(taskEntity));

        mockMvc.perform(MockMvcRequestBuilders.get("/getTasksByUserId?userId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L));

        verify(taskService, times(1)).getTasksByUserId(1L);
    }

    @Test
    void testGetTasksByUserIdException() throws Exception {
        when(taskService.getTasksByUserId(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/getTasksByUserId?userId=1"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(taskService, times(1)).getTasksByUserId(1L);
    }

    @Test
    void testGetTasksByTagSuccess() throws Exception {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTag("tag");
        when(taskService.getTasksByTag("tag")).thenReturn(Collections.singletonList(taskEntity));
        mockMvc.perform(MockMvcRequestBuilders.get("/getTasksByTag?tag=tag"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tag").value("tag"));

        verify(taskService, times(1)).getTasksByTag("tag");
    }

    @Test
    void testGetTasksByTagException() throws Exception {
        when(taskService.getTasksByTag("tag")).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/getTasksByTag?tag=tag"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(taskService, times(1)).getTasksByTag("tag");
    }

    @Test
    void testSearchTasksByTitleSuccess() throws Exception {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("title");
        when(taskService.searchTasksByTitle("title")).thenReturn(Collections.singletonList(taskEntity));

        mockMvc.perform(MockMvcRequestBuilders.get("/search?title=title"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"));

        verify(taskService, times(1)).searchTasksByTitle("title");
    }

    @Test
    void testSearchTasksByTitleException() throws Exception {
        when(taskService.searchTasksByTitle("title")).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/search?title=title"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(taskService, times(1)).searchTasksByTitle("title");
    }
}
