package com.luomsa.feeds.service;

import com.luomsa.feeds.dto.PostDto;
import com.luomsa.feeds.dto.PagePostDto;

public interface IPostService {
    PostDto createPost(String username, String title, String content);

    PagePostDto getLatestPosts(int page) throws InterruptedException;
    PagePostDto getCommentedPosts(int page);

//    void deletePost(String username, Long postId);
//
//    void likePost(String username, Long postId);
//
//    void unlikePost(String username, Long postId);
//
//    void commentPost(String username, Long postId, String content);
//
//    void deleteComment(String username, Long commentId);
}
