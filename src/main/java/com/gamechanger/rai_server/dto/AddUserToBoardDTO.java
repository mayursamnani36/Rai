package com.gamechanger.rai_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class AddUserToBoardDTO {
    private String board;
    private Long userId;
}
