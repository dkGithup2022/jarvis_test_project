package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
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
                DiscussionReply.class,
                DiscussionReplyWriter.class,
                ArticleWriterBase.class,
                ChildArticleEntity.class,
                IChildArticleEntityRepository.class,
                Popularity.class,
                PopularityEmbeddable.class,
                IUserEntityRepository.class,
                ArticleType.class
        }
)
public class DefaultDiscussionReplyWriter implements DiscussionReplyWriter {

    private final IChildArticleEntityRepository childArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public DiscussionReply write(DiscussionReply article) {
        if (article.getId() != null) {
            throw new RuntimeException("Article ID must not be present when writing a new article");
        }

        PopularityEmbeddable popularityEmbeddable = new PopularityEmbeddable(
                article.getPopularity().views(),
                article.getPopularity().likes(),
                article.getPopularity().dislikes(),
                article.getPopularity().comments()
        );

        ChildArticleEntity entity = ChildArticleEntity.of(
                article.getAuthorId(),
                ArticleType.DISCUSSION_REPLY,
                article.getTitle(),
                article.getContent(),
                popularityEmbeddable,
                article.getParentId(),
                article.getOrder(),
                article.isDeleted()
        );

        ChildArticleEntity savedEntity = childArticleEntityRepository.save(entity);

        return DiscussionReply.of(
                savedEntity.getId(),
                savedEntity.getAuthorId(),
                article.getAuthorNickname(), // Assuming the nickname is coming from the article directly
                savedEntity.getTitle(),
                savedEntity.getContent(),
                article.getPopularity(), // Popularity is passed directly from the article
                savedEntity.getParentId(),
                savedEntity.getOrder(),
                savedEntity.getDeleted()
        );
    }

    @Override
    public DiscussionReply update(DiscussionReply article) {
        if (article.getId() == null) {
            throw new RuntimeException("Article ID must be present when updating an article");
        }

        Optional<ChildArticleEntity> optionalEntity = childArticleEntityRepository.findById(article.getId());
        if (optionalEntity.isEmpty()) {
            throw new RuntimeException("Article does not exist");
        }

        ChildArticleEntity existingEntity = optionalEntity.get();
        
        PopularityEmbeddable popularityEmbeddable = new PopularityEmbeddable(
                article.getPopularity().views(),
                article.getPopularity().likes(),
                article.getPopularity().dislikes(),
                article.getPopularity().comments()
        );

        ChildArticleEntity updatedEntity = ChildArticleEntity.of(
                existingEntity.getId(),
                existingEntity.getAuthorId(),
                existingEntity.getArticleType(),
                article.getTitle(),
                article.getContent(),
                popularityEmbeddable,
                article.getParentId(),
                article.getOrder(),
                article.isDeleted()
        );

        ChildArticleEntity savedEntity = childArticleEntityRepository.save(updatedEntity);

        return DiscussionReply.of(
                savedEntity.getId(),
                savedEntity.getAuthorId(),
                article.getAuthorNickname(),
                savedEntity.getTitle(),
                savedEntity.getContent(),
                article.getPopularity(),
                savedEntity.getParentId(),
                savedEntity.getOrder(),
                savedEntity.getDeleted()
        );
    }

    @Override
    public void delete(Long id) {
        Optional<ChildArticleEntity> optionalEntity = childArticleEntityRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new RuntimeException("Article does not exist");
        }

        ChildArticleEntity existingEntity = optionalEntity.get();
        
        ChildArticleEntity deletedEntity = ChildArticleEntity.of(
                existingEntity.getId(),
                existingEntity.getAuthorId(),
                existingEntity.getArticleType(),
                existingEntity.getTitle(),
                existingEntity.getContent(),
                existingEntity.getPopularityEmbeddable(),
                existingEntity.getParentId(),
                existingEntity.getOrder(),
                true
        );

        childArticleEntityRepository.save(deletedEntity);
    }
}