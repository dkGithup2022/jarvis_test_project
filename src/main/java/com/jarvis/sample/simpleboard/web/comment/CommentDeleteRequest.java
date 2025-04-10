package com.jarvis.sample.simpleboard.web.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;

public record CommentDeleteRequest(
        ArticleType type,
        Long id
) {
}
