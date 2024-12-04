package com.luomsa.feeds.service;

import com.luomsa.feeds.dto.CommentDto;
import com.luomsa.feeds.dto.PageCommentDto;
import com.luomsa.feeds.dto.UserDto;
import com.luomsa.feeds.entity.Comment;
import com.luomsa.feeds.exception.NotFoundException;
import com.luomsa.feeds.repository.CommentRepository;
import com.luomsa.feeds.repository.PostRepository;
import com.luomsa.feeds.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDto createComment(String username, Long postId, String content) {
        var user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        var post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NotFoundException("Post not found");
        }
        var comment = new Comment(content, user.get(), post.get());
        commentRepository.save(comment);
        return new CommentDto(comment.getId(), comment.getContent(),
                new UserDto(comment.getAuthor().getId(), comment.getAuthor().getUsername()), comment.getCreatedAt());
    }

    public PageCommentDto getComments(long postId, int page) {
        var comments = commentRepository.getAllByIdOrderByCreatedAtDesc(postId, PageRequest.of(page, 20));
        return new PageCommentDto(comments.getContent().stream().map(comment -> new CommentDto(comment.getId(), comment.getContent(),
                new UserDto(comment.getAuthor().getId(), comment.getAuthor().getUsername()), comment.getCreatedAt())).toList(), comments.hasNext());
    }
}
