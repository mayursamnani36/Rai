package com.gamechanger.Rai_server.repository;

import com.gamechanger.Rai_server.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
