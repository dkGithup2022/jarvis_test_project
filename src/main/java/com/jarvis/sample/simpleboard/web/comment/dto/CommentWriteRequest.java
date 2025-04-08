package com.jarvis.sample.simpleboard.web.comment.dto;

import com.jarvis.sample.simpleboard.common.type.ArticleType;

public record CommentWriteRequest(
        ArticleType articleType,
        Long articleId,
        Long parentCommentId, // nullable, 대댓글이면 값 있음
        String content
) {}