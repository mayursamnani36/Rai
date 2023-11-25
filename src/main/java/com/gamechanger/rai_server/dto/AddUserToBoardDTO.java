package com.gamechanger.rai_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddUserToBoardDTO {
    String board;
    Long userId;
}
