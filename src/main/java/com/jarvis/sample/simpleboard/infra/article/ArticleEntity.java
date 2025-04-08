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
public class ArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;  // ✅ 작성자 ID 추가

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    private String title;

    private String content;

    private PopularityEmbeddable popularityEmbeddable;

    private Boolean deleted;

    public static ArticleEntity of(Long authorId, ArticleType articleType, String title, String content, PopularityEmbeddable popularityEmbeddable, Boolean deleted) {
        return new ArticleEntity(null, authorId, articleType, title, content, popularityEmbeddable, deleted);
    }

    public static ArticleEntity of(Long id, Long authorId, ArticleType articleType, String title, String content, PopularityEmbeddable popularityEmbeddable, Boolean deleted) {
        return new ArticleEntity(id, authorId, articleType, title, content, popularityEmbeddable, deleted);
    }

    private ArticleEntity(Long id, Long authorId, ArticleType articleType, String title, String content, PopularityEmbeddable popularityEmbeddable, Boolean deleted) {
        this.id = id;
        this.authorId = authorId;
        this.articleType = articleType;
        this.title = title;
        this.content = content;
        this.popularityEmbeddable = popularityEmbeddable;
        this.deleted = deleted;
    }
}