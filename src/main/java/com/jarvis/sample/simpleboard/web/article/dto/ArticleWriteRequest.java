package com.jarvis.sample.simpleboard.web.article.dto;

import com.jarvis.sample.simpleboard.common.type.ArticleType;

public record ArticleWriteRequest(ArticleType type,
                                  String title,
                                  Long parentId,
                                  String content) {
}
