package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { Answer.class, AnswerValidator.class,
        ArticleValidatorBase.class,
        ChildArticleEntity.class,
        IChildArticleEntityRepository.class,
        Popularity.class,
        PopularityEmbeddable.class,
        IUserEntityRepository.class,
        ArticleType.class }
)
@Component
@RequiredArgsConstructor
public class DefaultAnswerValidator implements AnswerValidator {

    private final IChildArticleEntityRepository childArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public boolean canWrite(Answer answer, User user) {
        return user != null && user.getUserRole() != null && !user.getUserRole().isEmpty() && answer.getId() == null;
    }

    @Override
    public boolean canUpdate(Answer answer, User user) {
        if (answer == null || user == null) {
            return false;
        }
        return answer.getAuthorId().equals(user.getUserId()) && articleAndUserExist(answer, user);
    }

    @Override
    public boolean canDelete(Answer answer, User user) {
        if (answer == null || user == null) {
            return false;
        }
        return answer.getAuthorId().equals(user.getUserId()) && articleAndUserExist(answer, user);
    }

    private boolean articleAndUserExist(Answer answer, User user) {
        Optional<ChildArticleEntity> articleEntity = childArticleEntityRepository.findById(answer.getId());
        Optional<UserEntity> userEntity = userEntityRepository.findById(user.getUserId());
        return articleEntity.isPresent() && userEntity.isPresent();
    }
}