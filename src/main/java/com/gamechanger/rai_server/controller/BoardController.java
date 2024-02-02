package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddBoardDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final RequestValidator requestValidator;

    @Autowired
    public BoardController(BoardService boardService, UserService userService, RequestValidator requestValidator) {
        this.boardService = boardService;
        this.userService = userService;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/createBoard")
    public ResponseEntity<String> createBoard(@RequestBody AddBoardDTO addBoardDTO) {
        try {
            log.info("addBoardDTO: {}", addBoardDTO);
            String title  = addBoardDTO.getTitle();
            if (StringUtils.isEmptyOrWhitespaceOnly(title)) {
                return ResponseEntity.badRequest().body("Title is required");
            }
            BoardEntity boardEntity = new BoardEntity();
            boardEntity.setTitle(title);
            boardService.saveBoard(boardEntity);
            log.info("Board created Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(title + " board created.");

        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Board already exists");
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/addUserToBoard")
    public ResponseEntity<String> addUserToBoard(@RequestBody AddUserToBoardDTO addUserToBoardDTO){
        try{
            log.info("addUserToBoardDTO: {}", addUserToBoardDTO);
            if(!requestValidator.validateUserToBoard(addUserToBoardDTO)){
                return ResponseEntity.badRequest().body("User/Board Doesn't exist in DB");
            }
            Long userId = addUserToBoardDTO.getUserId();
            String board = addUserToBoardDTO.getBoard();

            UserEntity dbUser = userService.findUserById(userId);
            BoardEntity dbBoard = boardService.getBoardByTitle(board);

            if(dbBoard.getUsers().contains(dbUser)){
                return ResponseEntity.badRequest().body("User with userId: " + userId.toString()+ " already exists in board: " + board);
            }
            boardService.addUsersToBoard(dbUser, dbBoard);
            log.info("User added to board");
            return ResponseEntity.status(HttpStatus.CREATED).body("User Added to Board " + addUserToBoardDTO.getBoard());
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
    @GetMapping("/getBoards")
    public ResponseEntity<List<BoardEntity>> getBoards(){
        try {
            List<BoardEntity> boardEntityList = boardService.getBoards();
            return ResponseEntity.ok(boardEntityList);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
