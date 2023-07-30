package com.gamechanger.Rai_server.service;

import com.gamechanger.Rai_server.entity.BoardEntity;

import java.util.List;

public interface BoardService {
    void saveBoard(BoardEntity boardEntity);

    List<BoardEntity> getBoards();
}
