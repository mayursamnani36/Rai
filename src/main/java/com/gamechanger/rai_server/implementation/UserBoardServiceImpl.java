package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.UserBoardEntity;
import com.gamechanger.rai_server.repository.UserBoardRepository;
import com.gamechanger.rai_server.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserBoardServiceImpl implements UserBoardService {

    @Autowired
    private UserBoardRepository userBoardRepository;
    @Override
    public void addUsersToBoard(AddUserToBoardDTO body) {
        String board = body.getBoard();
        List<Long> userList = body.getUsers();
        List<UserBoardEntity> userBoardEntityList = new ArrayList<>();
        for(Long userId : userList){
            UserBoardEntity userBoardEntity = new UserBoardEntity();
            userBoardEntity.setBoard(board);
            userBoardEntity.setUserId(userId);
            userBoardEntityList.add(userBoardEntity);
        }
        userBoardRepository.saveAll(userBoardEntityList);
    }
}
