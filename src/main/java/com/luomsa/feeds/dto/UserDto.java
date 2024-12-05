package com.luomsa.feeds.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDto(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String username) {
}
