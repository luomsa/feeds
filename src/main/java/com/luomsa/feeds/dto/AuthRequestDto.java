package com.luomsa.feeds.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRequestDto(
        @Size(min = 3, max = 12, message = "Username must be between 3 and 12 characters") @Pattern(regexp = "^[a-zAZ0-9]*$", message = "Username must contain only letters and numbers") String username,
        @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters") String password) {
}
