package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.BoardRepository;
import com.gamechanger.rai_server.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void saveBoard(BoardEntity boardEntity) {
        boardRepository.save(boardEntity);
    }

    @Override
    public List<BoardEntity> getBoards() {
        return boardRepository.findAll();
    }

    @Override
    public BoardEntity getBoardByTitle(String title) {
        return boardRepository.getBoardByTitle(title);
    }

    @Override
    public void addUsersToBoard(UserEntity dbUser, BoardEntity board) {
        board.getUsers().add(dbUser);
        boardRepository.save(board);
    }

}
