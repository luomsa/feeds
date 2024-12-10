package com.luomsa.feeds;

import com.luomsa.feeds.entity.Post;
import com.luomsa.feeds.entity.User;
import com.luomsa.feeds.repository.PostRepository;
import com.luomsa.feeds.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostService postService;

    @Test
    void testGetPostById() {
        var user = new User("user", "password");
        when(postRepository.findById(1L)).thenReturn(Optional.of(new Post("this is the title", "this-is-the-title", "content", user)));
        var postOptional = postRepository.findById(1L);
        verify(postRepository).findById(1L);
        var post = postOptional.get();
        assert post.getTitle().equals("this is the title");
        assert post.getSlug().equals("this-is-the-title");
        assert post.getContent().equals("content");
        assert post.getAuthor().equals(user);
    }

}
