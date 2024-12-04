package com.luomsa.feeds.service;

import com.luomsa.feeds.dto.CommentDto;
import com.luomsa.feeds.dto.PageCommentDto;

public interface ICommentService {
    CommentDto createComment(String username, Long postId, String content);

    PageCommentDto getComments(long postId, int page);
//    void deleteComment(Long commentId);
}
