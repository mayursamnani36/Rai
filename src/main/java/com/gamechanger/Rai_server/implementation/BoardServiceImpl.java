package com.gamechanger.Rai_server.implementation;

import com.gamechanger.Rai_server.entity.BoardEntity;
import com.gamechanger.Rai_server.repository.BoardRepository;
import com.gamechanger.Rai_server.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Override
    public void saveBoard(BoardEntity boardEntity) {
        boardRepository.save(boardEntity);
    }

    @Override
    public List<BoardEntity> getBoards() {
        return boardRepository.findAll();
    }
}
