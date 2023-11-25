package com.gamechanger.rai_server.repository;

import com.gamechanger.rai_server.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity getBoardByTitle(String title);
}
