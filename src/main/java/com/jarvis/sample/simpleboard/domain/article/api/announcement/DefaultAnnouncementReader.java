package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.PopularityMapper;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
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
        references = {Announcement.class, AnnouncementReader.class,
                ArticleReaderBase.class,
                UserEntity.class, IUserEntityRepository.class,
                ArticleEntity.class, IArticleEntityRepository.class,
                ArticleType.class, Popularity.class,
                PopularityMapper.class}
)
public class DefaultAnnouncementReader implements AnnouncementReader {

    private final IArticleEntityRepository articleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Announcement read(Long articleId) {
        Optional<ArticleEntity> articleEntityOpt = articleEntityRepository.findById(articleId);
        
        if (articleEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Article not found for id: " + articleId);
        }

        ArticleEntity articleEntity = articleEntityOpt.get();

        if (articleEntity.getArticleType() != ArticleType.ANNOUNCEMENT) {
            throw new IllegalArgumentException("Article is not of type ANNOUNCEMENT");
        }

        Optional<UserEntity> userEntityOpt = userEntityRepository.findById(articleEntity.getAuthorId());
        
        if (userEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found for id: " + articleEntity.getAuthorId());
        }

        UserEntity userEntity = userEntityOpt.get();

        Popularity popularity = PopularityMapper.toRead(articleEntity.getPopularityEmbeddable());

        return Announcement.of(
                articleEntity.getId(),
                articleEntity.getAuthorId(),
                userEntity.getNickname(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                popularity,
                articleEntity.getDeleted()
        );
    }
}