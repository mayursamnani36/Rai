package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.dto.AddBoardDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;

import java.util.List;

public interface BoardService {
    void saveBoard(AddBoardDTO addBoardDTO);

    List<BoardEntity> getBoards();

    BoardEntity getBoardByTitle(String title);

    void addUsersToBoard(AddUserToBoardDTO addUserToBoardDTO);
}
