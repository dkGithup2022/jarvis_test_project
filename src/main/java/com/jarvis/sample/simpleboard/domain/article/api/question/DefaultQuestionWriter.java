package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.PopularityMapper;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {
                Question.class,
                QuestionWriter.class,
                ArticleWriterBase.class,
                ParentArticleEntity.class,
                IParentArticleEntityRepository.class,
                ArticleType.class,
                Popularity.class
        }
)
public class DefaultQuestionWriter implements QuestionWriter {

    private final IParentArticleEntityRepository parentArticleEntityRepository;

    @Override
    public Question write(Question article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }

        ParentArticleEntity entity = ParentArticleEntity.of(
                ArticleType.QUESTION,
                article.getTitle(),
                article.getContent(),
                article.getAuthorId(),
                PopularityMapper.toEmbeddable(article.getPopularity()),
                false
        );

        ParentArticleEntity savedEntity = parentArticleEntityRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Question update(Question article) {
        if (article == null || article.getId() == null) {
            throw new IllegalArgumentException("Article and its ID cannot be null");
        }

        Optional<ParentArticleEntity> existingEntityOpt = parentArticleEntityRepository.findById(article.getId());
        if (existingEntityOpt.isEmpty()) {
            throw new RuntimeException("Article not found");
        }

        ParentArticleEntity existingEntity = existingEntityOpt.get();
        ParentArticleEntity updatedEntity = ParentArticleEntity.of(
                existingEntity.getId(),
                existingEntity.getArticleType(),
                article.getTitle(),
                article.getContent(),
                existingEntity.getAuthorId(),
                PopularityMapper.toEmbeddable(article.getPopularity()),
                existingEntity.getDeleted()
        );

        ParentArticleEntity savedEntity = parentArticleEntityRepository.save(updatedEntity);
        return mapToDomain(savedEntity);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Optional<ParentArticleEntity> entityOpt = parentArticleEntityRepository.findById(id);
        if (entityOpt.isEmpty()) {
            throw new RuntimeException("Article not found");
        }

        ParentArticleEntity entity = entityOpt.get();
        ParentArticleEntity deletedEntity = ParentArticleEntity.of(
                entity.getId(),
                entity.getArticleType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthorId(),
                entity.getPopularityEmbeddable(),
                true
        );

        parentArticleEntityRepository.save(deletedEntity);
    }

    private Question mapToDomain(ParentArticleEntity entity) {
        return Question.of(
                entity.getId(),
                entity.getArticleType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthorId(),
                null, // Assuming the nickname is not stored in ParentArticleEntity
                PopularityMapper.toRead(entity.getPopularityEmbeddable())
                , entity.getDeleted()
        );
    }
}