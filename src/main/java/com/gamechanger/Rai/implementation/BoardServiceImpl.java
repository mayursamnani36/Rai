package com.gamechanger.Rai.implementation;

import com.gamechanger.Rai.entity.BoardEntity;
import com.gamechanger.Rai.repository.BoardRepository;
import com.gamechanger.Rai.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Override
    public void saveBoard(BoardEntity boardEntity) {
        boardRepository.save(boardEntity);
    }
}
