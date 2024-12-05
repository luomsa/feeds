package com.luomsa.feeds.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PagePostDto(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<PostDto> posts,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        boolean hasMore
) {
}
