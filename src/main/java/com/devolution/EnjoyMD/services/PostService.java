package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.DTO.CommentDto;
import com.devolution.EnjoyMD.DTO.PostDto;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.LikeRepository;
import com.devolution.EnjoyMD.repository.PostRepository;
import com.devolution.EnjoyMD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public void addPost(User user, PostDto postDto, MultipartFile file) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("Authenticated user is required.");
        }

        Post post = new Post();
        post.setLocation(postDto.getLocation());
        post.setDescription(postDto.getDescription());
        post.setAuthor(user);

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

    public Post findPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PostDto> findPosts(int page, int size) {
        Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return postPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setLocation(post.getLocation());
        postDto.setDescription(post.getDescription());
        postDto.setFileName(post.getFileName());
        postDto.setAuthorUsername(post.getAuthor().getUsername());
        postDto.setLikeCount(post.getLikes().size());

        // Конвертация комментариев
        List<CommentDto> commentDtos = post.getComments().stream()
                .map(comment -> {
                    CommentDto dto = new CommentDto();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setAuthorUsername(comment.getAuthor().getUsername());
                    dto.setCreatedAtFormatted(String.valueOf(comment.getCreatedAt())); // Функция для форматирования даты
                    return dto;
                }).collect(Collectors.toList());

        postDto.setComments(commentDtos);
        return postDto;
    }

}

