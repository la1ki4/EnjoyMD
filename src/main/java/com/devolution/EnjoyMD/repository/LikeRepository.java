package com.devolution.EnjoyMD.repository;

import com.devolution.EnjoyMD.models.Like;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Like findByPostAndUser(Post post, User user);
}
