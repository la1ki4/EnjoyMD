package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Comment;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.services.CommentService;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/add")
    public String comment(@RequestParam("postId") Integer postId,
                             @RequestParam("content") String content,
                             @AuthenticationPrincipal MyUserDetails userDetails) {
        Post post = postService.findPostById(postId);
        User currentUser = userDetails.getUser();
        commentService.addComment(content, currentUser, post);
        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String comment(@PathVariable("postId") Integer postId, Model model) {
        Post post = postService.findPostById(postId);
        List<Comment> comments = commentService.getCommentsByPost(post);
        model.addAttribute("comments", comments);
        return "post-template";
    }
}
