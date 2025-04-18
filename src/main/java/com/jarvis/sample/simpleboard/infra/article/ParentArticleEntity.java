package com.jarvis.sample.simpleboard.infra.article;


import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.infra.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@JarvisMeta(
        fileType = FileType.INFRA_ENTITY
)
public class ParentArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    private String title;

    private String content;

    private Long authorId; // 작성자 ID 추가 ✅

    private PopularityEmbeddable popularityEmbeddable;

    private Boolean deleted;

    public static ParentArticleEntity of(
            ArticleType articleType,
            String title,
            String content,
            Long authorId,
            PopularityEmbeddable popularityEmbeddable,
            Boolean deleted
    ) {
        return new ParentArticleEntity(
                null, articleType, title, content, authorId, popularityEmbeddable, deleted
        );
    }

    public static ParentArticleEntity of(
            Long id,
            ArticleType articleType,
            String title,
            String content,
            Long authorId,
            PopularityEmbeddable popularityEmbeddable,
            Boolean deleted
    ) {
        return new ParentArticleEntity(
                id, articleType, title, content, authorId, popularityEmbeddable, deleted
        );
    }

    private ParentArticleEntity(
            Long id,
            ArticleType articleType,
            String title,
            String content,
            Long authorId,
            PopularityEmbeddable popularityEmbeddable,
            Boolean deleted
    ) {
        this.id = id;
        this.articleType = articleType;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.popularityEmbeddable = popularityEmbeddable;
        this.deleted = deleted;
    }
}