package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Like;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/")
    public String listPosts(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        if(userDetails != null) {
            return "mainAuth";
        }
        return "main";
    }

}
