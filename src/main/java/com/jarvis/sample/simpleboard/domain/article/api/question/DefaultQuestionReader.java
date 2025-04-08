package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.PopularityMapper;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
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
        references = {Question.class, QuestionReader.class,
                ArticleReaderBase.class, UserEntity.class,
                IUserEntityRepository.class, ParentArticleEntity.class,
                IParentArticleEntityRepository.class, ArticleType.class}
)
public class DefaultQuestionReader implements QuestionReader {

    private final IParentArticleEntityRepository parentArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Question read(Long articleId) {
        Optional<ParentArticleEntity> optionalArticleEntity = parentArticleEntityRepository.findById(articleId);
        if (optionalArticleEntity.isEmpty()) {
            throw new IllegalArgumentException("Article not found");
        }

        ParentArticleEntity articleEntity = optionalArticleEntity.get();
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(articleEntity.getAuthorId());
        if (optionalUserEntity.isEmpty()) {
            throw new IllegalArgumentException("Author not found");
        }

        UserEntity userEntity = optionalUserEntity.get();
        return mapToQuestion(articleEntity, userEntity.getNickname());
    }

    private Question mapToQuestion(ParentArticleEntity articleEntity, String authorNickname) {
        return Question.of(
                articleEntity.getId(),
                articleEntity.getArticleType(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthorId(),
                authorNickname,
                PopularityMapper.toRead(articleEntity.getPopularityEmbeddable()),
                articleEntity.getDeleted()
        );
    }
}