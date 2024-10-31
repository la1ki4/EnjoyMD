package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.DTO.CommentDto;
import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Comment;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.services.CommentService;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/add")
    public ResponseEntity<CommentDto> comment(
            @RequestParam("postId") Integer postId,
            @RequestParam("content") String content,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        Post post = postService.findPostById(postId);
        User currentUser = userDetails.getUser();

        Comment comment = commentService.addComment(content, currentUser, post);

        CommentDto response = new CommentDto();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setAuthorUsername(comment.getAuthor().getUsername());
        response.setCreatedAtFormatted(comment.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

