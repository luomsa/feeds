package com.luomsa.feeds.controller;

import com.luomsa.feeds.dto.CommentRequestDto;
import com.luomsa.feeds.dto.PostRequestDto;
import com.luomsa.feeds.service.CommentService;
import com.luomsa.feeds.service.PostService;
import com.luomsa.feeds.service.UserService;
import com.sun.net.httpserver.HttpContext;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page) {
        var feed = postService.getPosts(page);
        return ResponseEntity.ok(feed);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequestDto request) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var post = postService.createPost(username, request.title(), request.content());
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> createComment(@PathVariable long postId, @Valid @RequestBody CommentRequestDto request) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var comment = commentService.createComment(username, postId, request.content());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable long postId, @RequestParam(defaultValue = "0") int page) {
        var comments = commentService.getComments(postId, page);
        return ResponseEntity.ok(comments);
    }
}