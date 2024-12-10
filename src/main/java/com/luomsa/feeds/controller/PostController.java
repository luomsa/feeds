package com.luomsa.feeds.controller;

import com.luomsa.feeds.dto.*;
import com.luomsa.feeds.service.CommentService;
import com.luomsa.feeds.service.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<PagePostDto> getPosts(@RequestParam(defaultValue = "latest") String sort, @RequestParam(defaultValue = "0") int page) {
        PagePostDto feed = "comments".equals(sort) ? postService.getCommentedPosts(page) : postService.getLatestPosts(page);
        return ResponseEntity.ok(feed);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId) {
        var post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostRequestDto request) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var post = postService.createPost(username, request.title(), request.content());
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentWithPageDto> createComment(@PathVariable long postId, @Valid @RequestBody CommentRequestDto request) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var comment = commentService.createComment(username, postId, request.content());
        var count = commentService.getCommentCount(postId);
        var page = (count - 1) / 20;
        var commentWithPage = new CommentWithPageDto(comment.id(), comment.content(), comment.author(), comment.createdAt(), page);
        return ResponseEntity.ok(commentWithPage);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<PageCommentDto> getComments(@PathVariable long postId, @RequestParam(defaultValue = "0") int page) {
        var comments = commentService.getComments(postId, page);
        return ResponseEntity.ok(comments);
    }
}
