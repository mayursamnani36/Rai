package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.dto.AddBoardDTO;
import com.gamechanger.rai_server.dto.AddUserToBoardDTO;
import com.gamechanger.rai_server.entity.BoardEntity;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.BoardRepository;
import com.gamechanger.rai_server.service.BoardService;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final RequestValidator requestValidator;
    private final UserService userService;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, RequestValidator requestValidator, UserService userService) {
        this.boardRepository = boardRepository;
        this.requestValidator = requestValidator;
        this.userService = userService;
    }

    @Override
    public void saveBoard(AddBoardDTO addBoardDTO) {
        String title = addBoardDTO.getTitle();
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(title);
        boardRepository.save(boardEntity);
    }

    @Override
    public List<BoardEntity> getBoards() {
        return boardRepository.findAll();
    }

    @Override
    public BoardEntity getBoardByTitle(String title) {
        return boardRepository.getBoardByTitle(title);
    }

    @Override
    public void addUsersToBoard(AddUserToBoardDTO addUserToBoardDTO) {
        if (getBoardByTitle(addUserToBoardDTO.getBoard())==null || !requestValidator.validateUserToBoard(addUserToBoardDTO)) {
            throw new IllegalArgumentException("User/Board doesn't exist in DB");
        }
        Long userId = addUserToBoardDTO.getUserId();
        String boardTitle = addUserToBoardDTO.getBoard();

        UserEntity dbUser = userService.findUserById(userId);
        BoardEntity dbBoard = getBoardByTitle(boardTitle);

        if (dbBoard.getUsers().contains(dbUser)) {
            throw new IllegalArgumentException("User with userId: " + userId + " already exists in board: " + boardTitle);
        }
        dbBoard.getUsers().add(dbUser);
        boardRepository.save(dbBoard);
    }
}
