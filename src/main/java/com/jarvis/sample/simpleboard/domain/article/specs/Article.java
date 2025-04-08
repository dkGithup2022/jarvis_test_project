package com.jarvis.sample.simpleboard.domain.article.specs;



import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleBase;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;

@JarvisMeta(
        fileType = FileType.DOMAIN_SPEC
)
@Getter
public class Article implements ArticleBase {

    private static final int MAX_LENGTH = 10_000;

    private Long id;
    private Long authorId;
    private String authorNickname;

    private String title;
    private String content;
    private Popularity popularity;
    private Boolean deleted;

    public static Article of(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, Boolean deleted) {
        return new Article(id, authorId, authorNickname, title, content, popularity, deleted);
    }

    private Article(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, Boolean deleted) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        if (content == null || content.length() > MAX_LENGTH)
            throw new IllegalArgumentException("내용은 10,000자를 초과할 수 없습니다.");
        this.id = id;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
        this.title = title;
        this.content = content;
        this.popularity = popularity;
        this.deleted = deleted;
    }
}