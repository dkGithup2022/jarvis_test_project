package com.jarvis.sample.simpleboard.domain.article.specs;


import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleBase;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;

@JarvisMeta(
        fileType = FileType.DOMAIN_SPEC
)
@Getter
public class Question implements ArticleBase {
    private static final int CONTENT_MAX_LENGTH = 10_000;

    private Long id;
    private ArticleType articleType;
    private String title;
    private String content;
    private Long authorId;
    private String authorNickname;
    private Popularity popularity;
    private Boolean deleted;

    public static Question of(
            Long id,
            ArticleType articleType,
            String title,
            String content,
            Long authorId,
            String authorNickname,
            Popularity popularity,
            Boolean deleted
    ) {
        return new Question(id, articleType, title, content, authorId, authorNickname, popularity, deleted);
    }

    private Question(
            Long id,
            ArticleType articleType,
            String title,
            String content,
            Long authorId,
            String authorNickname,
            Popularity popularity,
            Boolean deleted
    ) {
        validateContent(content);
        this.id = id;
        this.articleType = articleType;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
        this.popularity = popularity;
        this.deleted = deleted;
    }

    private void validateContent(String content) {
        if (content == null || content.length() > CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException("내용이 비어있거나 10,000자를 초과할 수 없습니다.");
        }
    }
}
