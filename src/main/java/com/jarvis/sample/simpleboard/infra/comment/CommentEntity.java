package com.jarvis.sample.simpleboard.infra.comment;


import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.infra.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@JarvisMeta(
        fileType = FileType.INFRA_ENTITY
)
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ArticleType articleType;

    private Long articleId;

    private String content;

    private Long parentId; // null이면 댓글, 값이 있으면 대댓글

    private Integer rootCommentOrder; // 댓글 정렬용

    private Integer replyOrder; // 대댓글 정렬용

    private Integer childCount = 0; // 대댓글 수

    private boolean deleted = false; // soft delete


    public static CommentEntity of(Long id, ArticleType articleType, Long articleId, String content, Long parentId, Integer rootCommentOrder, Integer replyOrder, Integer childCount, boolean deleted) {
        return new CommentEntity(id, articleType, articleId, content, parentId, rootCommentOrder, replyOrder, childCount, deleted);
    }

    private CommentEntity(Long id, ArticleType articleType, Long articleId, String content, Long parentId, Integer rootCommentOrder, Integer replyOrder, Integer childCount, boolean deleted) {
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