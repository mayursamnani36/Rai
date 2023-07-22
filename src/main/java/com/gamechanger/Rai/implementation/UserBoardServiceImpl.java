package com.gamechanger.Rai.implementation;

import com.gamechanger.Rai.dto.AddUserToBoardDTO;
import com.gamechanger.Rai.entity.UserBoardEntity;
import com.gamechanger.Rai.repository.UserBoardRepository;
import com.gamechanger.Rai.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBoardServiceImpl implements UserBoardService {

    @Autowired
    UserBoardRepository userBoardRepository;
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
