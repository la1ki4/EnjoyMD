package com.devolution.EnjoyMD.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private List<CategoryDto> categories;
    private int commentCount;
    private int likeCount;
    private int viewCount;
    private LocalDateTime createdAt;
}
