package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
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
    public DiscussionReply write(DiscussionReply reply) {
        validateUser(reply.getAuthorId());
        ChildArticleEntity entity = mapToEntity(reply);
        ChildArticleEntity savedEntity = childArticleEntityRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    private void validateUser(Long authorId) {
        if (authorId == null || userEntityRepository.findById(authorId).isEmpty()) {
            throw new IllegalArgumentException("Invalid author ID.");
        }
    }

    private ChildArticleEntity mapToEntity(DiscussionReply reply) {
        PopularityEmbeddable popularityEmbeddable = new PopularityEmbeddable(
            reply.getPopularity().views(),
            reply.getPopularity().likes(),
            reply.getPopularity().dislikes(),
            reply.getPopularity().comments()
        );

        return ChildArticleEntity.of(
            reply.getId(),
            reply.getAuthorId(),
            ArticleType.DISCUSSION_REPLY,
            reply.getTitle(),
            reply.getContent(),
            popularityEmbeddable,
            reply.getParentId(),
            reply.getOrder(),
            reply.isDeleted()
        );
    }

    private DiscussionReply mapToDomain(ChildArticleEntity entity) {
        Popularity popularity = Popularity.of(
            entity.getPopularityEmbeddable().getViews(),
            entity.getPopularityEmbeddable().getLikes(),
            entity.getPopularityEmbeddable().getDislikes(),
            entity.getPopularityEmbeddable().getComments()
        );

        return DiscussionReply.of(
            entity.getId(),
            entity.getAuthorId(),
            "", // Assuming authorNickname is fetched from another source, e.g., a user repository
            entity.getTitle(),
            entity.getContent(),
            popularity,
            entity.getParentId(),
            entity.getOrder(),
            entity.getDeleted()
        );
    }
}