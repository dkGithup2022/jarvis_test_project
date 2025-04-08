package com.jarvis.sample.simpleboard.domain.comment.specs;



import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;

@JarvisMeta(
        fileType = FileType.DOMAIN_SPEC
)
@Getter
public class Comment {

    private static final int MAX_CONTENT_LENGTH = 2000;

    private Long id;
    private ArticleType articleType;
    private Long articleId;
    private String content;
    private Long parentId;
    private Integer rootCommentOrder;
    private Integer replyOrder;
    private Integer childCount;
    private boolean deleted;

    public static Comment of(
            Long id,
            ArticleType articleType,
            Long articleId,
            String content,
            Long parentId,
            Integer rootCommentOrder,
            Integer replyOrder,
            Integer childCount,
            boolean deleted
    ) {
        return new Comment(id, articleType, articleId, content, parentId, rootCommentOrder, replyOrder, childCount, deleted);
    }

    public Comment markDeleted() {
        return new Comment(
                id,
                articleType,
                articleId,
                content,
                parentId,
                rootCommentOrder,
                replyOrder,
                childCount,
                true
        );
    }

    public boolean isReply() {
        return parentId != null;
    }

    public static boolean isValidContent(String content) {
        return content != null && !content.isBlank() && content.length() <= MAX_CONTENT_LENGTH;
    }

    private Comment(
            Long id,
            ArticleType articleType,
            Long articleId,
            String content,
            Long parentId,
            Integer rootCommentOrder,
            Integer replyOrder,
            Integer childCount,
            boolean deleted
    ) {
        if (!isValidContent(content)) {
            throw new IllegalArgumentException("Invalid comment content");
        }
        this.id = id;
        this.articleType = articleType;
        this.articleId = articleId;
        this.content = content;
        this.parentId = parentId;
        this.rootCommentOrder = rootCommentOrder;
        this.replyOrder = replyOrder;
        this.childCount = childCount;
        this.deleted = deleted;
    }
}