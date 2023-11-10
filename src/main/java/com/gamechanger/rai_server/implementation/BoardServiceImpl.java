package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.repository.BoardRepository;
import com.gamechanger.rai_server.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Override
    public void saveBoard(BoardEntity boardEntity) {
        boardRepository.save(boardEntity);
    }

    @Override
    public List<BoardEntity> getBoards() {
        return boardRepository.findAll();
    }
}
