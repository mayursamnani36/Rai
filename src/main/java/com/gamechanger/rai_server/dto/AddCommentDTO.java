package com.gamechanger.rai_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class AddCommentDTO {
    private String comment;
    private Long userId;
    private Long taskId;
}
