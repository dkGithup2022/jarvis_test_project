package com.jarvis.sample.simpleboard.domain.comment.api.comment;


import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.List;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface CommentReader {
    List<Comment> listByArticleInfo(ArticleType articleType, Long articleId, int page, int pageSize);
}
