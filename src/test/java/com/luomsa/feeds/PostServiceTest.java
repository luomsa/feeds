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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

        assertEquals("this is the title", post.title());
        assertEquals("this-is-the-title", post.slug());
        assertEquals("content", post.content());
        assertEquals("user", post.author().username());
    }

    @Test
    void testGetCommentedPosts() {
        var user = new User("user", "password");
        var post = new Post("title", "title", "content", user);
        var postPage = new PageImpl<>(List.of(post));
        when(postRepository.findAllByOrderByComments(any(PageRequest.class))).thenReturn(postPage);

        var pagePostDto = postService.getCommentedPosts(0);

        verify(postRepository).findAllByOrderByComments(any(PageRequest.class));

        assertEquals(1, pagePostDto.posts().size());
        assertEquals("title", pagePostDto.posts().get(0).title());
    }

    @Test
    void testGetLatestPosts() {
        var user = new User("user", "password");
        var post = new Post("title", "title", "content", user);
        var postPage = new PageImpl<>(List.of(post));
        when(postRepository.findAllByOrderByCreatedAt(any(PageRequest.class))).thenReturn(postPage);

        var pagePostDto = postService.getLatestPosts(0);

        verify(postRepository).findAllByOrderByCreatedAt(any(PageRequest.class));

        assertEquals(1, pagePostDto.posts().size());
        assertEquals("title", pagePostDto.posts().get(0).title());

    }


}
