package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
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
        references = {Article.class, ArticleWriter.class,
                ArticleWriterBase.class,
                UserEntity.class, IUserEntityRepository.class,
                ArticleEntity.class, IArticleEntityRepository.class,
                ArticleType.class, Popularity.class}
)
public class DefaultArticleWriter implements ArticleWriter {

    private final IArticleEntityRepository articleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Article write(Article article) {
        if (article.getId() != null) {
            throw new RuntimeException("Article ID must not be present when writing a new article.");
        }

        UserEntity userEntity = userEntityRepository.findById(article.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        ArticleEntity articleEntity = ArticleEntity.of(
                article.getAuthorId(),
                ArticleType.NORMAL, // Default type, could be parameterized if needed
                article.getTitle(),
                article.getContent(),
                new PopularityEmbeddable(article.getPopularity()),
                false // Not deleted by default
        );

        ArticleEntity savedArticleEntity = articleEntityRepository.save(articleEntity);

        return Article.of(
                savedArticleEntity.getId(),
                savedArticleEntity.getAuthorId(),
                userEntity.getNickname(),
                savedArticleEntity.getTitle(),
                savedArticleEntity.getContent(),
                new Popularity(savedArticleEntity.getPopularityEmbeddable()),
                savedArticleEntity.getDeleted()
        );
    }

    @Override
    public Article update(Article article) {
        if (article.getId() == null) {
            throw new RuntimeException("Article ID must be present when updating an article.");
        }

        ArticleEntity articleEntity = articleEntityRepository.findById(article.getId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        articleEntity = ArticleEntity.of(
                articleEntity.getId(),
                articleEntity.getAuthorId(),
                articleEntity.getArticleType(),
                article.getTitle(),
                article.getContent(),
                articleEntity.getPopularityEmbeddable(),
                articleEntity.getDeleted()
        );

        ArticleEntity updatedArticleEntity = articleEntityRepository.save(articleEntity);

        return Article.of(
                updatedArticleEntity.getId(),
                updatedArticleEntity.getAuthorId(),
                article.getAuthorNickname(),
                updatedArticleEntity.getTitle(),
                updatedArticleEntity.getContent(),
                new Popularity(updatedArticleEntity.getPopularityEmbeddable()),
                updatedArticleEntity.getDeleted()
        );
    }

    @Override
    public void delete(Long id) {
        ArticleEntity articleEntity = articleEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        ArticleEntity deletedArticleEntity = ArticleEntity.of(
                articleEntity.getId(),
                articleEntity.getAuthorId(),
                articleEntity.getArticleType(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getPopularityEmbeddable(),
                true // Set the deleted flag to true
        );

        articleEntityRepository.save(deletedArticleEntity);
    }
}

// Note: This implementation assumes the existence of a `PopularityEmbeddable` class or similar to handle the conversion between `Popularity` and its embedded representation in the `ArticleEntity`. 
// Further, the `ArticleType` is defaulted to `NORMAL` during writing, which can be adjusted based on specific requirements.