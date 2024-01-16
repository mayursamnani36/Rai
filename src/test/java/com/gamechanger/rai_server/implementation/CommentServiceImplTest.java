package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.CommentEntity;
import com.gamechanger.rai_server.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void testAddComment() {
        CommentEntity commentEntity = new CommentEntity();

        commentService.addComment(commentEntity);

        verify(commentRepository, times(1)).save(commentEntity);
    }

    @Test
    public void testGetCommentsByTaskId() {
        Long taskId = 1L;
        List<CommentEntity> expectedComments = new ArrayList<>();
        when(commentRepository.getCommentsByTaskId(taskId)).thenReturn(expectedComments);

        List<CommentEntity> actualComments = commentService.getCommentsByTaskId(taskId);

        assertEquals(expectedComments, actualComments);
        verify(commentRepository, times(1)).getCommentsByTaskId(taskId);
    }
}
