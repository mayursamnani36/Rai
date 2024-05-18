package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.AddBoardDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.service.BoardService;
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

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/createBoard")
    public ResponseEntity<String> createBoard(@RequestBody AddBoardDTO addBoardDTO) {
        log.info("addBoardDTO: {}", addBoardDTO);
        try {
            String title = addBoardDTO.getTitle();
            if (StringUtils.isEmptyOrWhitespaceOnly(title)) {
                return ResponseEntity.badRequest().body("Title is required");
            }
            boardService.saveBoard(addBoardDTO);
            log.info("Board created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(title + " board created.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Board already exists");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/addUserToBoard")
    public ResponseEntity<String> addUserToBoard(@RequestBody AddUserToBoardDTO addUserToBoardDTO){
        log.info("addUserToBoardDTO: {}", addUserToBoardDTO);
        try {
            boardService.addUsersToBoard(addUserToBoardDTO);
            log.info("User added to board");
            return ResponseEntity.status(HttpStatus.CREATED).body("User added to board: " + addUserToBoardDTO.getBoard());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
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
