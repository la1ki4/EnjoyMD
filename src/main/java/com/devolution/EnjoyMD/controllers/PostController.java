package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.DTO.PostDto;
import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/add")
    public String postForm(Model model) {
        model.addAttribute("postDto", new PostDto()); // Измените на PostDto
        return "post-form";
    }

    @PostMapping("/add")
    public String addPost(@AuthenticationPrincipal MyUserDetails userDetails,
                          @ModelAttribute PostDto postDto, // Измените на PostDto
                          @RequestParam("file") MultipartFile file
    ) throws IOException {

        User user = userDetails.getUser();

        if (user == null) {
            throw new IllegalArgumentException("Authenticated user is required.");
        }

        postDto.setAuthorUsername(user.getUsername());
        postService.addPost(user, postDto, file);
        return "redirect:/";
    }

    @GetMapping("/load")
    public ResponseEntity<List<PostDto>> loadPosts(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "3") int size) {
        List<PostDto> posts = postService.findPosts(page, size);
        return ResponseEntity.ok(posts);
    }

}

