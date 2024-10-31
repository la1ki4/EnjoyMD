package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.PostView;
import com.devolution.EnjoyMD.repository.PostRepository;
import com.devolution.EnjoyMD.repository.PostViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostViewServiceImpl implements PostViewService {

    @Autowired
    private PostViewRepository postViewRepository;
    @Autowired
    private PostRepository postRepository;
    private static final Duration VIEW_INTERVAL = Duration.ofHours(1);


    @Override
    public int addView(Integer postId, Integer userId, String ipAddress) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("Post not found");
        }

        Post post = postOpt.get();
        LocalDateTime oneHourAgo = LocalDateTime.now().minus(VIEW_INTERVAL);
        boolean shouldCount = false;

        if (userId != null) {
            boolean hasViewed = postViewRepository.existsByPostIdAndUserIdAndViewedAtAfter(postId, userId, oneHourAgo);
            if (!hasViewed) {
                shouldCount = true;
            }
        } else if (ipAddress != null) {
            boolean hasViewed = postViewRepository.existsByPostIdAndIpAddressAndViewedAtAfter(postId, ipAddress, oneHourAgo);
            if (!hasViewed) {
                shouldCount = true;
            }
        }

        if (shouldCount) {
            PostView postView = new PostView();
            postView.setPost(post); // Устанавливаем объект Post
            postView.setUserId(userId);
            postView.setIpAddress(ipAddress);
            postView.setViewedAt(LocalDateTime.now());

            postViewRepository.save(postView);
        }

        return postViewRepository.countByPostId(postId);
    }

    @Override
    public int getViewCount(Integer postId) {
        return postViewRepository.countByPostId(postId);
    }
}
