package com.gamechanger.Rai.controller;

import com.gamechanger.Rai.dto.AddUserToBoardDTO;
import com.gamechanger.Rai.entity.BoardEntity;
import com.gamechanger.Rai.service.BoardService;
import com.gamechanger.Rai.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    UserBoardService userBoardService;
    @PostMapping("/createBoard")
    public String createBoard(@RequestBody String name) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(name);
        boardService.saveBoard(boardEntity);
        return name + " board created.";
    }
    @PostMapping("/addUsersToBoard")
    public String addUsersToBoard(@RequestBody AddUserToBoardDTO body){
        userBoardService.addUsersToBoard(body);
        return "Users Added to Board " + body.getBoard();
    }
}
