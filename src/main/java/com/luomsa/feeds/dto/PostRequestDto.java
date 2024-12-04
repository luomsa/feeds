package com.luomsa.feeds.dto;

import jakarta.validation.constraints.Size;

public record PostRequestDto(
        @Size(min = 1, max = 40, message = "Title must be between 1 and 40 characters") String title,
        @Size(min = 1, max = 140, message = "Content must be between 1 and 140 characters") String content) {
}
