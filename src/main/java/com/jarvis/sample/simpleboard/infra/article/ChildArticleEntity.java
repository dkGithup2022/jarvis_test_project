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
public class ChildArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    private Long authorId;

    private String title;

    private String content;

    private PopularityEmbeddable popularityEmbeddable;

    private Long parentId;

    private Integer order;

    private Boolean deleted;

    public static ChildArticleEntity of(Long authorId, ArticleType articleType, String title, String content, PopularityEmbeddable popularityEmbeddable, Long parentId, Integer order, Boolean deleted) {
        return new ChildArticleEntity(null, authorId, articleType, title, content, popularityEmbeddable, parentId, order, deleted);
    }

    public static ChildArticleEntity of(Long id, Long authorId, ArticleType articleType, String title, String content, PopularityEmbeddable popularityEmbeddable, Long parentId, Integer order, Boolean deleted) {
        return new ChildArticleEntity(id, authorId, articleType, title, content, popularityEmbeddable, parentId, order, deleted);
    }

    private ChildArticleEntity(Long id, Long authorId, ArticleType articleType, String title, String content, PopularityEmbeddable popularityEmbeddable, Long parentId, Integer order, Boolean deleted) {
        this.id = id;
        this.authorId = authorId;
        this.articleType = articleType;
        this.title = title;
        this.content = content;
        this.popularityEmbeddable = popularityEmbeddable;
        this.parentId = parentId;
        this.order = order;
        this.deleted = deleted;
    }
}