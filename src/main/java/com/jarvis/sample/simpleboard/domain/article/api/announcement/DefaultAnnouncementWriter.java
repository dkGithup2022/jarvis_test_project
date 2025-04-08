package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleWriter;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = {
        Announcement.class,
        AnnouncementWriter.class,
        ArticleWriterBase.class,
        UserEntity.class, IUserEntityRepository.class,
        ArticleEntity.class, IArticleEntityRepository.class,
        ArticleType.class, Popularity.class
    }
)
public class DefaultAnnouncementWriter implements AnnouncementWriter {

    private final IArticleEntityRepository articleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Announcement write(Announcement announcement) {
        if (announcement.getId() != null) {
            throw new IllegalArgumentException("New announcement must not have an ID.");
        }

        Optional<UserEntity> author = userEntityRepository.findById(announcement.getAuthorId());
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author does not exist.");
        }

        ArticleEntity entity = ArticleEntity.of(
            announcement.getAuthorId(),
            ArticleType.ANNOUNCEMENT,
            announcement.getTitle(),
            announcement.getContent(),
            null, // Assuming popularity is handled separately
            false // New announcements are not deleted
        );

        ArticleEntity savedEntity = articleEntityRepository.save(entity);
        return Announcement.of(
            savedEntity.getId(),
            savedEntity.getAuthorId(),
            author.get().getNickname(),
            savedEntity.getTitle(),
            savedEntity.getContent(),
            announcement.getPopularity(),
            savedEntity.getDeleted()
        );
    }

    @Override
    public Announcement update(Announcement announcement) {
        if (announcement.getId() == null) {
            throw new IllegalArgumentException("Announcement must have an ID to be updated.");
        }

        Optional<ArticleEntity> existingEntityOpt = articleEntityRepository.findById(announcement.getId());
        if (existingEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Announcement does not exist.");
        }

        ArticleEntity existingEntity = existingEntityOpt.get();
        existingEntity = ArticleEntity.of(
            existingEntity.getId(),
            existingEntity.getAuthorId(),
            existingEntity.getArticleType(),
            announcement.getTitle(),
            announcement.getContent(),
            existingEntity.getPopularityEmbeddable(),
            existingEntity.getDeleted()
        );

        ArticleEntity updatedEntity = articleEntityRepository.save(existingEntity);
        Optional<UserEntity> author = userEntityRepository.findById(updatedEntity.getAuthorId());

        return Announcement.of(
            updatedEntity.getId(),
            updatedEntity.getAuthorId(),
            author.map(UserEntity::getNickname).orElse("Unknown"),
            updatedEntity.getTitle(),
            updatedEntity.getContent(),
            announcement.getPopularity(),
            updatedEntity.getDeleted()
        );
    }

    @Override
    public void delete(Long id) {
        Optional<ArticleEntity> entityOpt = articleEntityRepository.findById(id);
        if (entityOpt.isEmpty()) {
            throw new IllegalArgumentException("Announcement does not exist.");
        }

        ArticleEntity entity = entityOpt.get();
        entity = ArticleEntity.of(
            entity.getId(),
            entity.getAuthorId(),
            entity.getArticleType(),
            entity.getTitle(),
            entity.getContent(),
            entity.getPopularityEmbeddable(),
            true // Mark as deleted
        );

        articleEntityRepository.save(entity);
    }
}