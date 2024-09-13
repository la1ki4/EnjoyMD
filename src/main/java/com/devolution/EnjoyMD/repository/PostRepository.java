package com.devolution.EnjoyMD.repository;

import com.devolution.EnjoyMD.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
