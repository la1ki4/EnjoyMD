package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/main")
    public String listPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "mainAuth";
    }

}
