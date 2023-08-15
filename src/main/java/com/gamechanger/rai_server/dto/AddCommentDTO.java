package com.gamechanger.rai_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCommentDTO {
    String comment;
    Long userId;
    Long taskId;
}
