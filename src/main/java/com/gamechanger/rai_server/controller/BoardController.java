package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.UserBoardService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserBoardService userBoardService;
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
    @PostMapping("/addUsersToBoard")
    public String addUsersToBoard(@RequestBody AddUserToBoardDTO body){
        try{
            if(!requestValidator.validateUserToBoard(body)){
                throw new Exception("Invalid body");
            }
            userBoardService.addUsersToBoard(body);
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
