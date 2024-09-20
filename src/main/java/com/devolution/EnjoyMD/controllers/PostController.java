package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    @GetMapping("/add")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "post-form";
    }


    @PostMapping("/add")
    public String addPost(@AuthenticationPrincipal MyUserDetails userDetails,
                          @RequestParam String location,
                          @RequestParam("file") MultipartFile file,
                          @RequestParam String description
    ) throws IOException {

        User user = userDetails.getUser();

        if (user == null) {
            throw new IllegalArgumentException("Authenticated user is required.");
        }

        postService.addPost(user,location,file,description);
        return "redirect:/main";
    }
}
