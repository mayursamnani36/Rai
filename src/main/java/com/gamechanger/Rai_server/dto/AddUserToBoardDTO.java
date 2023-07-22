package com.gamechanger.Rai_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddUserToBoardDTO {
    String board;
    List<Long> users;
}
