package com.luomsa.feeds.dto;

import jakarta.validation.constraints.Size;

public record PostRequestDto(
        @Size(min = 1, max = 80, message = "Title must be between 1 and 80 characters") String title,
        @Size(min = 1, max = 400, message = "Content must be between 1 and 400 characters") String content) {
}
