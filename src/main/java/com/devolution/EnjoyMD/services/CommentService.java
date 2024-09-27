package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.Comment;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;

import java.util.List;

public interface CommentService {
    Comment addComment(String content, User author , Post post);
    List<Comment> getCommentsByPost(Post post);
}
