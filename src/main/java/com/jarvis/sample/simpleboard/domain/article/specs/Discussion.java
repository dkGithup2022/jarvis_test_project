package com.jarvis.sample.simpleboard.domain.article.specs;



import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleBase;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_SPEC
)
public class Discussion   implements ArticleBase {

    private static final int MAX_CONTENT_LENGTH = 10_000;
    private final Long id;
    private final Long authorId;
    private final String authorNickname;
    private final String title;
    private final String content;
    private final Popularity popularity;
    private final boolean deleted;

    public static Discussion of(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, boolean deleted) {
        return new Discussion(id, authorId, authorNickname, title, content, popularity, deleted);
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
