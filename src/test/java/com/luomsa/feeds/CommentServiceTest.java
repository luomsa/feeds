package com.luomsa.feeds;

import com.luomsa.feeds.repository.CommentRepository;
import com.luomsa.feeds.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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

}
