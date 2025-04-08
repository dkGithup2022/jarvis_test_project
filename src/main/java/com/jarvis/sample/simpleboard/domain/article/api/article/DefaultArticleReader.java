package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = {Article.class, ArticleReader.class,
            ArticleReaderBase.class, UserEntity.class, IUserEntityRepository.class,
            ArticleEntity.class, IArticleEntityRepository.class,
            ArticleType.class, Popularity.class}
)
public class DefaultArticleReader implements ArticleReader {

    private final IArticleEntityRepository articleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Article read(Long articleId) {
        ArticleEntity articleEntity = articleEntityRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found for id: " + articleId));

        Optional<UserEntity> authorEntity = userEntityRepository.findById(articleEntity.getAuthorId());
        String authorNickname = authorEntity.map(UserEntity::getNickname).orElse("Unknown");

        return Article.of(
                articleEntity.getId(),
                articleEntity.getAuthorId(),
                authorNickname,
                articleEntity.getTitle(),
                articleEntity.getContent(),
                mapPopularity(articleEntity),
                articleEntity.getDeleted()
        );
    }

    private Popularity mapPopularity(ArticleEntity articleEntity) {
        return new Popularity(
                articleEntity.getPopularityEmbeddable().getViews(),
                articleEntity.getPopularityEmbeddable().getLikes(),
                articleEntity.getPopularityEmbeddable().getDislikes(),
                articleEntity.getPopularityEmbeddable().getComments()
        );
    }
}