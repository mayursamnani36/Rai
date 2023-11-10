package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;
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
    //TODO: check if board exists or users exists
    public String addUsersToBoard(@RequestBody AddUserToBoardDTO body){
        userBoardService.addUsersToBoard(body);
        return "Users Added to Board " + body.getBoard();
    }
    @GetMapping("/getBoards")
    public List<BoardEntity> getBoards(){
        return boardService.getBoards();
    }
}
