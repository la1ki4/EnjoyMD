package com.devolution.EnjoyMD.repository;

import com.devolution.EnjoyMD.models.PostView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostViewRepository extends JpaRepository<PostView, Integer> {
    int countByPostId(int postId);
    boolean existsByPostIdAndUserIdAndViewedAtAfter(int postId, Integer userId, LocalDateTime after);
    boolean existsByPostIdAndIpAddressAndViewedAtAfter(int postId, String ipAddress, LocalDateTime oneHourAgo);
}
