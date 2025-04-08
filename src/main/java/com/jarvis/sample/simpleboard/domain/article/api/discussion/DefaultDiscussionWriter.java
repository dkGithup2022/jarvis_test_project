package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.PopularityMapper;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {Discussion.class, DiscussionWriter.class, ArticleWriterBase.class,
                UserEntity.class, IUserEntityRepository.class,
                ParentArticleEntity.class, IParentArticleEntityRepository.class,
                ArticleType.class, UserRole.class, Popularity.class}
)
public class DefaultDiscussionWriter implements DiscussionWriter {

    private final IParentArticleEntityRepository parentArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Autowired
    public DefaultDiscussionWriter(IParentArticleEntityRepository parentArticleEntityRepository,
                                   IUserEntityRepository userEntityRepository) {
        this.parentArticleEntityRepository = parentArticleEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Discussion write(Discussion article) {
        if (article.getId() != null) {
            throw new RuntimeException("Article ID must be null for a new article");
        }

        UserEntity author = userEntityRepository.findById(article.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        ParentArticleEntity entity = ParentArticleEntity.of(
                ArticleType.DISCUSSION,
                article.getTitle(),
                article.getContent(),
                author.getId(),
                PopularityMapper.toEmbeddable(article.getPopularity()),
                false
        );

        ParentArticleEntity savedEntity = parentArticleEntityRepository.save(entity);

        return Discussion.of(savedEntity.getId(), savedEntity.getAuthorId(),
                author.getNickname(), savedEntity.getTitle(),
                savedEntity.getContent(), article.getPopularity(), false);
    }

    @Override
    public Discussion update(Discussion article) {
        ParentArticleEntity entity = parentArticleEntityRepository.findById(article.getId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        entity = ParentArticleEntity.of(
                entity.getId(),
                ArticleType.DISCUSSION,
                article.getTitle(),
                article.getContent(),
                entity.getAuthorId(),
                entity.getPopularityEmbeddable(),
                entity.getDeleted()
        );

        ParentArticleEntity updatedEntity = parentArticleEntityRepository.save(entity);

        UserEntity author = userEntityRepository.findById(updatedEntity.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        return Discussion.of(updatedEntity.getId(), updatedEntity.getAuthorId(),
                author.getNickname(), updatedEntity.getTitle(),
                updatedEntity.getContent(), article.getPopularity(), updatedEntity.getDeleted());
    }

    @Override
    public void delete(Long id) {
        ParentArticleEntity entity = parentArticleEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        entity = ParentArticleEntity.of(
                entity.getId(),
                entity.getArticleType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthorId(),
                entity.getPopularityEmbeddable(),
                true
        );

        parentArticleEntityRepository.save(entity);
    }
}