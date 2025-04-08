package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.PopularityMapper;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
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
        references = {Discussion.class, DiscussionReader.class, ArticleReaderBase.class,
                UserEntity.class, IUserEntityRepository.class,
                ParentArticleEntity.class, IParentArticleEntityRepository.class,
                ArticleType.class, Popularity.class}
)
public class DefaultDiscussionReader implements DiscussionReader {

    private final IParentArticleEntityRepository parentArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Discussion read(Long articleId) {
        Optional<ParentArticleEntity> articleEntityOpt = parentArticleEntityRepository.findById(articleId);
        if (articleEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Article not found");
        }

        ParentArticleEntity articleEntity = articleEntityOpt.get();
        if (articleEntity.getArticleType() != ArticleType.DISCUSSION) {
            throw new IllegalArgumentException("Invalid article type");
        }

        Long authorId = articleEntity.getAuthorId();
        Optional<UserEntity> userEntityOpt = userEntityRepository.findById(authorId);
        if (userEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        UserEntity userEntity = userEntityOpt.get();

        return Discussion.of(
                articleEntity.getId(),
                articleEntity.getAuthorId(),
                userEntity.getNickname(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                PopularityMapper.toRead(articleEntity.getPopularityEmbeddable()),
                articleEntity.getDeleted()
        );
    }
}