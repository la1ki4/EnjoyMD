package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.Like;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.LikeRepository;
import com.devolution.EnjoyMD.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public Integer toggleLike(int postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> null);
        Like like = likeRepository.findByPostAndUser(post, user);

        if (like != null) {
            likeRepository.delete(like); // Удалить лайк, если он уже существует
        } else {
            Like newLike = new Like();
            newLike.setPost(post);
            newLike.setUser(user);
            likeRepository.save(newLike); // Добавить новый лайк
        }
        return postRepository.findById(postId).isPresent() ? postRepository.findById(postId).get().getLikes().size() : null;
    }
}
