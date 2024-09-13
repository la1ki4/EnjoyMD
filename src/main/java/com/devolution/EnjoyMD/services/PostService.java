package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.config.MyUserDetails;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.PostRepository;
import com.devolution.EnjoyMD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private PostRepository postRepository;
    @Value("${upload.path}")
    private String uploadPath;
    private UserRepository userRepository;

    PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public void addPost(User user,
                        String location,
                        MultipartFile file,
                        String description
    ) throws IOException {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser.isEmpty()) {
            throw new IllegalArgumentException("User not found in the database");
        }

        User actualUser = existingUser.get();

        Post post = new Post();
        post.setLocation(location);
        post.setDescription(description);
        post.setAuthor(actualUser);

        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            post.setFileName("/images/" + resultFileName);
        }

        postRepository.save(post);
    }

    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }
}
