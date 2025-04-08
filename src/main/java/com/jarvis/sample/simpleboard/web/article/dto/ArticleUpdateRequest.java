package com.jarvis.sample.simpleboard.web.article.dto;

import com.jarvis.sample.simpleboard.common.type.ArticleType;

public record ArticleUpdateRequest(
        ArticleType type,

        Long articleId,

        String title,
        String content
) {
}
