package com.luomsa.feeds;

import com.luomsa.feeds.entity.Comment;
import com.luomsa.feeds.entity.Post;
import com.luomsa.feeds.entity.User;
import com.luomsa.feeds.repository.CommentRepository;
import com.luomsa.feeds.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    void testCountCommentsByPostId() {
        when(commentRepository.countCommentsByPostId(1L)).thenReturn(10);
        var count = commentService.getCommentCount(1L);
        verify(commentRepository).countCommentsByPostId(1L);
        assert count == 10;
    }

    @Test
    void testGetComments() {
        var author = new User("user", "password");
        var post = new Post("this a title", "this-is-a-title", "content", author);
        var comment = new Comment("this is a comment", author, post);
        var commentPage = new PageImpl<>(List.of(comment));
        when(commentRepository.getAllByPostIdOrderByCreatedAt(1L, PageRequest.of(0, 20))).thenReturn(commentPage);
        var page = commentService.getComments(1L, 0);
        verify(commentRepository).getAllByPostIdOrderByCreatedAt(1L, PageRequest.of(0, 20));
        assertEquals("this is a comment", page.comments().getFirst().content());
    }

}
