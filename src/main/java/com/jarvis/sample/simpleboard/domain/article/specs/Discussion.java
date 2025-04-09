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
public class Discussion implements ArticleBase {

    private static final int MAX_CONTENT_LENGTH = 10_000;
    private Long id;
    private Long authorId;
    private String authorNickname;
    private String title;
    private String content;
    private Popularity popularity;
    private boolean deleted;

    public static Discussion of(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, boolean deleted) {
        return new Discussion(id, authorId, authorNickname, title, content, popularity, deleted);
    }

    @Override
    public void updateTitle(String title) {
        this.title = title;
    }

    @Override
    public void updateContent(String content) {
        this.content = content;
    }

    private Discussion(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, boolean deleted) {
        if (content == null || content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("Content must be non-null and up to " + MAX_CONTENT_LENGTH + " characters");
        }
        this.id = id;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
        this.title = title;
        this.content = content;
        this.popularity = popularity;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
