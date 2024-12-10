package com.luomsa.feeds.dto;

import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @Size(min = 1, max = 240, message = "Content must be between 1 and 240 characters")
        String content) {
}
