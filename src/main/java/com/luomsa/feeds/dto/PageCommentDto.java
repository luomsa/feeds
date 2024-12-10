package com.luomsa.feeds.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PageCommentDto(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<CommentDto> comments,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean hasMore,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int totalPages
        ) {
}
