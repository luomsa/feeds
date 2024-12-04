package com.luomsa.feeds.dto;

import java.util.List;

public record PageCommentDto(List<CommentDto> comments, boolean hasMore) {
}
