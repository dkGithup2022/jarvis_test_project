package com.jarvis.sample.simpleboard.domain.article.api.answer;

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
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { Answer.class, AnswerReader.class,
            ArticleReaderBase.class,
            ChildArticleEntity.class,
            IChildArticleEntityRepository.class,
            Popularity.class,
            PopularityEmbeddable.class,
            IUserEntityRepository.class,
            ArticleType.class
    }
)
public class DefaultAnswerReader implements AnswerReader {

    private final IChildArticleEntityRepository childArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Answer read(Long articleId) {
        Optional<ChildArticleEntity> childArticleEntityOpt = childArticleEntityRepository.findById(articleId);
        if (childArticleEntityOpt.isEmpty() || childArticleEntityOpt.get().getArticleType() != ArticleType.ANSWER) {
            throw new IllegalArgumentException("Invalid article ID or not an answer type");
        }
        ChildArticleEntity childArticleEntity = childArticleEntityOpt.get();
        String authorNickname = userEntityRepository.findById(childArticleEntity.getAuthorId())
                .map(UserEntity::getNickname)
                .orElse("Unknown");
        return mapToDomain(childArticleEntity, authorNickname);
    }

    private Answer mapToDomain(ChildArticleEntity entity, String authorNickname) {
        Popularity popularity = Popularity.of(
                entity.getPopularityEmbeddable().getViews(),
                entity.getPopularityEmbeddable().getLikes(),
                entity.getPopularityEmbeddable().getDislikes(),
                entity.getPopularityEmbeddable().getComments()
        );
        return Answer.of(
                entity.getId(),
                entity.getAuthorId(),
                authorNickname,
                entity.getTitle(),
                entity.getContent(),
                popularity,
                entity.getParentId(),
                entity.getOrder(),
                entity.getDeleted()
        );
    }

    public List<Answer> listByParentId(Long parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // TODO: 메모기록- 근절해야 할 구현체
        return childArticleEntityRepository.listByParentIdOrderByOrder(parentId, pageable).stream()
                .filter(entity -> entity.getArticleType() == ArticleType.ANSWER)
                .map(entity -> {
                    String authorNickname = userEntityRepository.findById(entity.getAuthorId())
                            .map(UserEntity::getNickname)
                            .orElse("Unknown");
                    return mapToDomain(entity, authorNickname);
                })
                .collect(Collectors.toList());
    }
}