package com.devolution.EnjoyMD.TestServices;

import com.devolution.EnjoyMD.models.Post;
import com.devolution.EnjoyMD.repository.PostRepository;
import com.devolution.EnjoyMD.repository.PostViewRepository;
import com.devolution.EnjoyMD.services.PostViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostViewServiceTest {

    @Autowired
    private PostViewService postViewService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostViewRepository postViewRepository;

    @Test
    public void testAddView() {
        // Создайте тестовый пост
        Post post = new Post();
        post.setFileName("test.jpg");
        post.setLocation("Test Location");
        post.setDescription("Test Description");
        post = postRepository.save(post);

        // Добавьте просмотр
        int initialCount = postViewService.getViewCount(post.getId());
        int newCount = postViewService.addView(post.getId(), null, "127.0.0.1");

        assertEquals(initialCount + 1, newCount);
    }
}

