package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.BoardRepository;
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
public class BoardServiceImplTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    @Test
    public void testSaveBoard() {
        BoardEntity boardEntity = new BoardEntity();

        boardService.saveBoard(boardEntity);

        verify(boardRepository, times(1)).save(boardEntity);
    }

    @Test
    public void testGetBoards() {
        List<BoardEntity> expectedBoards = new ArrayList<>();
        when(boardRepository.findAll()).thenReturn(expectedBoards);

        List<BoardEntity> actualBoards = boardService.getBoards();

        assertEquals(expectedBoards, actualBoards);
        verify(boardRepository, times(1)).findAll();
    }

    @Test
    public void testGetBoardByTitle() {
        String title = "TestBoard";
        BoardEntity expectedBoard = new BoardEntity();
        when(boardRepository.getBoardByTitle(title)).thenReturn(expectedBoard);

        BoardEntity actualBoard = boardService.getBoardByTitle(title);

        assertEquals(expectedBoard, actualBoard);
        verify(boardRepository, times(1)).getBoardByTitle(title);
    }

    @Test
    public void testAddUsersToBoard() {
        UserEntity dbUser = new UserEntity();
        BoardEntity board = new BoardEntity();

        boardService.addUsersToBoard(dbUser, board);

        verify(boardRepository, times(1)).save(board);
    }
}
