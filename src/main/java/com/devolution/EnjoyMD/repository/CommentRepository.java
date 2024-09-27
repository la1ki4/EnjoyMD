package com.devolution.EnjoyMD.repository;

import com.devolution.EnjoyMD.models.Comment;
import com.devolution.EnjoyMD.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}
