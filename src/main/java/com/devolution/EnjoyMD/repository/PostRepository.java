package com.devolution.EnjoyMD.repository;

import com.devolution.EnjoyMD.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p JOIN p.categories c WHERE c.name IN :categoryNames GROUP BY p.id")
    List<Post> findByCategoryNames(@Param("categoryNames") List<String> categoryName);
}
