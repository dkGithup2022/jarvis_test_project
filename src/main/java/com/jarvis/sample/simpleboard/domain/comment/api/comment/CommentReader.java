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

    /*
    * comment 의 id 로 조회,
    * 조회 결과가 없다면 null 을 출력하시오.
    * */
    Comment findById(Long id);
}
