package com.luomsa.feeds.dto;

import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @Size(min = 1, max = 140, message = "Content must be between 1 and 140 characters")
        String content) {
}
