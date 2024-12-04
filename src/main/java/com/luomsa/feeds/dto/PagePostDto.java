package com.luomsa.feeds.dto;
import java.util.List;

public record PagePostDto(
        List<PostDto> posts,
        boolean hasMore
) {
}
