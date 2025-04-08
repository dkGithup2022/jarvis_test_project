package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {DiscussionReply.class, DiscussionReplyReader.class,
                ArticleReaderBase.class,
                ChildArticleEntity.class,
                IChildArticleEntityRepository.class,
                Popularity.class,
                PopularityEmbeddable.class,
                IUserEntityRepository.class,
                ArticleType.class
        }
)
public class DefaultDiscussionReplyReader implements DiscussionReplyReader {

    private final IChildArticleEntityRepository childArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public DiscussionReply read(Long articleId) {
        Optional<ChildArticleEntity> articleEntityOptional = childArticleEntityRepository.findById(articleId);
        if (articleEntityOptional.isEmpty() || articleEntityOptional.get().getDeleted()) {
            throw new IllegalArgumentException("Article not found or has been deleted.");
        }

        ChildArticleEntity articleEntity = articleEntityOptional.get();
        if (articleEntity.getArticleType() != ArticleType.DISCUSSION_REPLY) {
            throw new IllegalArgumentException("Incorrect article type for Discussion Reply.");
        }

        String authorNickname = userEntityRepository.findById(articleEntity.getAuthorId())
                .map(UserEntity::getNickname)
                .orElse("Unknown");

        Popularity popularity = toPopularity(articleEntity.getPopularityEmbeddable());

        return DiscussionReply.of(
                articleEntity.getId(),
                articleEntity.getAuthorId(),
                authorNickname,
                articleEntity.getTitle(),
                articleEntity.getContent(),
                popularity,
                articleEntity.getParentId(),
                articleEntity.getOrder(),
                articleEntity.getDeleted()
        );
    }

    private Popularity toPopularity(PopularityEmbeddable embeddable) {
        return Popularity.of(
                embeddable.getViews(),
                embeddable.getLikes(),
                embeddable.getDislikes(),
                embeddable.getComments()
        );
    }
}