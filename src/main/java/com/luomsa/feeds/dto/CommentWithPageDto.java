package com.luomsa.feeds.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record CommentWithPageDto(@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
                                 Long id,
                                 @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
                                 String content,
                                 @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
                                 UserDto author,
                                 @Schema(requiredMode = Schema.RequiredMode.REQUIRED, type = "string")
                                 Instant createdAt,
                                 @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
                                 int page) {
}
