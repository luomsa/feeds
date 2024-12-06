package com.luomsa.feeds.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRequestDto(
        @Size(min = 3, max = 14, message = "Username must be between 3 and 14 characters") @Pattern(regexp = "^[a-zAZ0-9]*$", message = "Username must contain only letters and numbers") String username,
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters") String password) {
}
