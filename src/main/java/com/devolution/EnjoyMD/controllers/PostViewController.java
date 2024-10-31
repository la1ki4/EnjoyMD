package com.devolution.EnjoyMD.controllers;


import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.PostRepository;
import com.devolution.EnjoyMD.services.PostViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostViewController {

    private PostViewService postViewService;

    private PostRepository postRepository;

    @PostMapping("/{postId}/view")
    public ResponseEntity<?> addView(@PathVariable Integer postId, HttpServletRequest request, Authentication authentication) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        Integer userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            userId = user.getUserId();
        }

        String ipAddress = request.getRemoteAddr();

        int viewCount = postViewService.addView(postId, userId, ipAddress);

        Map<String, Integer> response = new HashMap<>();
        response.put("viewCount", viewCount);

        return ResponseEntity.ok(response);
    }
}
