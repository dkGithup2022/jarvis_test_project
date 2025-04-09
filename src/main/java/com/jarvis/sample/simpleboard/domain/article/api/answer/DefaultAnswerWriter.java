package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {
                Answer.class, AnswerWriter.class,
                DefaultAnswerWriter.class,

                ChildArticleEntity.class,
                IChildArticleEntityRepository.class,

                UserEntity.class,
                IUserEntityRepository.class,

                Popularity.class,
                PopularityEmbeddable.class,

                IUserEntityRepository.class,
                ArticleType.class,
                UserRole.class
        }
)
public class DefaultAnswerWriter implements AnswerWriter {

    private final IChildArticleEntityRepository childArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Answer write(Answer article) {
        verifyUserExists(article.getAuthorId());

        ChildArticleEntity entity = ChildArticleEntity.of(
                article.getAuthorId(),
                ArticleType.ANSWER,
                article.getTitle(),
                article.getContent(),
                convertToEmbeddable(article.getPopularity()),
                article.getParentId(),
                article.getOrder(),
                article.isDeleted()
        );

        ChildArticleEntity savedEntity = childArticleEntityRepository.save(entity);
        return convertToAnswer(savedEntity);
    }

    @Override
    public Answer update(Answer article) {
        verifyUserExists(article.getAuthorId());

        ChildArticleEntity entity = childArticleEntityRepository.findById(article.getId())
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        entity = ChildArticleEntity.of(
                entity.getId(),
                article.getAuthorId(),
                ArticleType.ANSWER,
                article.getTitle(),
                article.getContent(),
                convertToEmbeddable(article.getPopularity()),
                article.getParentId(),
                article.getOrder(),
                article.isDeleted()
        );

        ChildArticleEntity updatedEntity = childArticleEntityRepository.save(entity);
        return convertToAnswer(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        ChildArticleEntity entity = childArticleEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        ChildArticleEntity deletedEntity = ChildArticleEntity.of(
                entity.getId(),
                entity.getAuthorId(),
                entity.getArticleType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPopularityEmbeddable(),
                entity.getParentId(),
                entity.getOrder(),
                true // Set as deleted
        );

        childArticleEntityRepository.save(deletedEntity);
    }

    private void verifyUserExists(Long userId) {
        userEntityRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private PopularityEmbeddable convertToEmbeddable(Popularity popularity) {
        return new PopularityEmbeddable(
                popularity.views(),
                popularity.likes(),
                popularity.dislikes(),
                popularity.comments()
        );
    }

    private Answer convertToAnswer(ChildArticleEntity entity) {
        return Answer.of(
                entity.getId(),
                entity.getAuthorId(),
                retrieveAuthorNickname(entity.getAuthorId()),
                entity.getTitle(),
                entity.getContent(),
                new Popularity(
                        entity.getPopularityEmbeddable().getViews(),
                        entity.getPopularityEmbeddable().getLikes(),
                        entity.getPopularityEmbeddable().getDislikes(),
                        entity.getPopularityEmbeddable().getComments()
                ),
                entity.getParentId(),
                entity.getOrder(),
                entity.getDeleted()
        );
    }

    private String retrieveAuthorNickname(Long authorId) {
        return userEntityRepository.findById(authorId)
                .map(UserEntity::getNickname)
                .orElse(null);
    }
}