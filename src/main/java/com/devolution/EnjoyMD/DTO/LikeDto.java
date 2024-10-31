package com.devolution.EnjoyMD.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private int id;
    private int userId;
    private int postId;
    private boolean liked;
    private int likeCount;
}
