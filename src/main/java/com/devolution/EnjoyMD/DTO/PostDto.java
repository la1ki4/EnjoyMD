package com.devolution.EnjoyMD.DTO;

import com.devolution.EnjoyMD.models.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int id;
    private String location;
    private String description;
    private String fileName;
    private String authorUsername;
    private List<CommentDto> comments;
    private int commentCount;
    private int likeCount;
    private int viewCount;
    private LocalDateTime createdAt;
}
