package com.devolution.EnjoyMD.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private String authorUsername;
    private String createdAtFormatted;
}
