 package com.luomsa.feeds.dto;

import java.time.Instant;

public record CommentDto(Long id, String content, UserDto author, Instant createdAt) {
}
