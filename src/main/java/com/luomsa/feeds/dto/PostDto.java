package com.luomsa.feeds.dto;

import java.time.Instant;

public record PostDto(Long id, String title, String content, UserDto author, int commentCount, Instant createdAt) {
}
