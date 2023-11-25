package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;
    @Autowired
    private RequestValidator requestValidator;

    @PostMapping("/createBoard")
    public String createBoard(@RequestBody String title) {
        try {
            if (title.isEmpty()) {
                return "Empty title";
            }
            BoardEntity boardEntity = new BoardEntity();
            boardEntity.setTitle(title);
            boardService.saveBoard(boardEntity);
            return title + " board created.";
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
    @PostMapping("/addUserToBoard")
    public String addUsersToBoard(@RequestBody AddUserToBoardDTO body){
        try{
            if(!requestValidator.validateUserToBoard(body)){
                throw new Exception("Invalid body");
            }
            UserEntity dbUser = userService.findUserById(body.getUserId());
            BoardEntity dbBoard = boardService.getBoardByTitle(body.getBoard());
            if(dbBoard.getUsers().contains(dbUser)){throw new Exception("Given user Already Exists in the given board");}
            boardService.addUsersToBoard(dbUser, dbBoard);
            return "Users Added to Board " + body.getBoard();
        }
        catch (Exception ex){
            return ex.getMessage();
        }
    }
    @GetMapping("/getBoards")
    public List<BoardEntity> getBoards(){
        return boardService.getBoards();
    }
}
