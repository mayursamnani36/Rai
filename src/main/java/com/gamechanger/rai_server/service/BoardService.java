package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;

import java.util.List;

public interface BoardService {
    void saveBoard(BoardEntity boardEntity);

    List<BoardEntity> getBoards();

    BoardEntity getBoardByTitle(String title);

    void addUsersToBoard(UserEntity dbUser, BoardEntity board);
}
