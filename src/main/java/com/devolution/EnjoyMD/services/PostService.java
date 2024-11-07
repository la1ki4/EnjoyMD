package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.DTO.CategoryDto;
import com.devolution.EnjoyMD.DTO.CommentDto;
import com.devolution.EnjoyMD.DTO.PostDto;
import com.devolution.EnjoyMD.models.Category;
import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.CategoryRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CategoryRepository categoryRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void addPost(User user, PostDto postDto, MultipartFile file, List<Integer> categoryIds) throws IOException {
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

        List<Category> categories = new ArrayList<>(categoryRepository.findAllById(categoryIds));
        post.setCategories(categories);
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
        List<CommentDto> commentDto = post.getComments().stream()
                .map(comment -> {
                    CommentDto dto = new CommentDto();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setAuthorUsername(comment.getAuthor().getUsername());
                    dto.setCreatedAtFormatted(String.valueOf(comment.getCreatedAt()));
                    return dto;
                })
                .collect(Collectors.toList());
        postDto.setComments(commentDto);

        List<CategoryDto> categoriesDto = post.getCategories().stream()
                .map(category -> {
                    CategoryDto dto = new CategoryDto();
                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    return dto;
                })
                .collect(Collectors.toList());
        postDto.setCategories(categoriesDto);


        return postDto;
    }

    public List<PostDto> getPostsByCategories(List<String> categoryNames) {

        if (categoryNames == null || categoryNames.isEmpty()) {
            return Collections.emptyList(); // Вернуть пустой список, если нет категорий
        }

        return postRepository.findByCategoryNames(categoryNames).stream()
                .map(post -> new PostDto(
                        post.getId(),
                        post.getLocation(),
                        post.getDescription(),
                        post.getFileName(),
                        post.getAuthor().getUsername(),
                        post.getComments().stream()
                                .map(comment -> new CommentDto(/* параметры */))
                                .collect(Collectors.toList()),
                        post.getCategories().stream()
                                .map(category -> new CategoryDto(category.getId(), category.getName()))
                                .collect(Collectors.toList()),
                        post.getComments().size(),
                        post.getLikes().size(),
                        post.getViews().size(),
                        post.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

}

