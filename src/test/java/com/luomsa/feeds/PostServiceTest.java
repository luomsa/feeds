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
        when(postRepository.findPostById(1L)).thenReturn(Optional.of(new Post("this is the title", "this-is-the-title", "content", user)));
        var post = postService.getPost(1L);
        verify(postRepository).findPostById(1L);

        assert post.title().equals("this is the title");
        assert post.slug().equals("this-is-the-title");
        assert post.content().equals("content");
        assert post.author().username().equals("user");}

}
