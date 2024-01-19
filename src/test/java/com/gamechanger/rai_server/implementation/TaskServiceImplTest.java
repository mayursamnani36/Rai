package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTask() {
        TaskEntity taskEntity = new TaskEntity();

        taskService.createTask(taskEntity);

        verify(taskRepository, times(1)).save(taskEntity);
    }

    @Test
    void testGetTaskByTaskId() {
        Long taskId = 1L;
        TaskEntity expectedTask = new TaskEntity();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        TaskEntity actualTask = taskService.getTaskByTaskId(taskId);

        assertEquals(expectedTask, actualTask);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testGetTasksByUserId() {
        Long userId = 1L;
        List<TaskEntity> expectedTasks = new ArrayList<>();
        when(taskRepository.getTasksByAssignee(userId)).thenReturn(expectedTasks);

        List<TaskEntity> actualTasks = taskService.getTasksByUserId(userId);

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).getTasksByAssignee(userId);
    }

    @Test
    void testGetTasksByTag() {
        String tag = "TestTag";
        List<TaskEntity> expectedTasks = new ArrayList<>();
        when(taskRepository.getTasksByTag(tag)).thenReturn(expectedTasks);

        List<TaskEntity> actualTasks = taskService.getTasksByTag(tag);

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).getTasksByTag(tag);
    }

    @Test
    void testSearchTasksByTitle() {
        String title = "TestTitle";
        List<TaskEntity> expectedTasks = new ArrayList<>();
        when(taskRepository.findByTitleContaining(title)).thenReturn(expectedTasks);

        List<TaskEntity> actualTasks = taskService.searchTasksByTitle(title);

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findByTitleContaining(title);
    }
}
