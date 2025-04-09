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
public class DiscussionReply   implements ArticleBase {
    private static final int MAX_CONTENT_LENGTH = 10_000;

    private Long id;
    private Long authorId;
    private String authorNickname;

    private String title;
    private String content;

    private Popularity popularity;
    private Long parentId;
    private Integer order;
    private boolean deleted;

    public static DiscussionReply of(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, Long parentId, Integer order, boolean deleted) {
        validateContent(content);
        return new DiscussionReply(id, authorId, authorNickname, title, content, popularity, parentId, order, deleted);
    }


    @Override
    public void updateTitle(String title) {
        this.title =title;
    }

    @Override
    public void updateContent(String content) {
        this.content = content;
    }
    private DiscussionReply(Long id, Long authorId, String authorNickname, String title, String content, Popularity popularity, Long parentId, Integer order, boolean deleted) {
        this.id = id;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
        this.title = title;
        this.content = content;
        this.popularity = popularity;
        this.parentId = parentId;
        this.order = order;
        this.deleted = deleted;
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank() || content.length() > MAX_CONTENT_LENGTH)
            throw new IllegalArgumentException("내용이 너무 길거나 비어 있습니다.");
    }
}
