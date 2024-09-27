package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.services.LikeService;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/main")
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final PostService postService;

    @GetMapping("/showLike/{postId}")
    public ResponseEntity<Map<String,Integer>> toggleLike(@PathVariable int postId) {
        Post currentPost = postService.findPostById(postId);
        int likesCount = currentPost.getLikes().size();

        Map<String,Integer> response = new HashMap<>();
        response.put("likesCount", likesCount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggleLike/{postId}")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable int postId, @AuthenticationPrincipal MyUserDetails userDetails) {
        User currentUser = userDetails.getUser();
        int updLikeCount = likeService.toggleLike(postId,currentUser);

        Map<String,Object> response = new HashMap<>();
        response.put("likeCount", updLikeCount);

        return response;
    }
}
