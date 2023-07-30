package com.gamechanger.Rai_server.controller;

import com.gamechanger.Rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.Rai_server.entity.BoardEntity;
import com.gamechanger.Rai_server.service.BoardService;
import com.gamechanger.Rai_server.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
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
    @GetMapping("/getBoards")
    public List<BoardEntity> getBoards(){
        return boardService.getBoards();
    }
}
