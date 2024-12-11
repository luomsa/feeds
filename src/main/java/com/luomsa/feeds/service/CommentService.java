package com.luomsa.feeds.service;

import com.luomsa.feeds.dto.CommentDto;
import com.luomsa.feeds.dto.PageCommentDto;
import com.luomsa.feeds.dto.UserDto;
import com.luomsa.feeds.entity.Comment;
import com.luomsa.feeds.exception.NotFoundException;
import com.luomsa.feeds.repository.CommentRepository;
import com.luomsa.feeds.repository.PostRepository;
import com.luomsa.feeds.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public CommentDto createComment(String username, Long postId, String content) {
        var user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        var postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new NotFoundException("Post not found");
        }
        var post = postOptional.get();
        var comment = new Comment(content, user.get(), post);
        commentRepository.save(comment);
        post.setLatestCommentAt(Instant.now());
        postRepository.save(post);
        return new CommentDto(comment.getId(), comment.getContent(),
                new UserDto(comment.getAuthor().getId(), comment.getAuthor().getUsername()), comment.getCreatedAt());
    }

    public PageCommentDto getComments(long postId, int page) {
        var comments = commentRepository.getAllByPostIdOrderByCreatedAt(postId, PageRequest.of(page, 20));
        return new PageCommentDto(comments.getContent().stream().map(comment -> new CommentDto(comment.getId(), comment.getContent(),
                new UserDto(comment.getAuthor().getId(), comment.getAuthor().getUsername()), comment.getCreatedAt())).toList(), comments.hasNext(), comments.getTotalPages());
    }

    public int getCommentCount(long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }
}
