package com.gamechanger.rai_server.service;

import com.gamechanger.rai_server.dto.AddUserToBoardDTO;

public interface UserBoardService {
    void addUsersToBoard(AddUserToBoardDTO body);
}
