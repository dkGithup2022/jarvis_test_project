package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {Question.class, QuestionValidator.class, ArticleValidatorBase.class,
                UserEntity.class, IUserEntityRepository.class,
                ParentArticleEntity.class, IParentArticleEntityRepository.class,
                ArticleType.class, UserRole.class, Popularity.class}
)
@RequiredArgsConstructor
public class DefaultQuestionValidator implements QuestionValidator {

    private final IParentArticleEntityRepository parentArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public boolean canWrite(Question article, User user) {
        if (article.getId() != null) {
            return false;
        }
        Set<UserRole> userRoles = userEntityRepository.findById(user.getUserId())
                .map(UserEntity::getUserRole)
                .orElse(Set.of());
        return userRoles.contains(UserRole.USER) || userRoles.contains(UserRole.ADMIN) || userRoles.contains(UserRole.SYSTEM);
    }

    @Override
    public boolean canUpdate(Question article, User user) {
        Optional<UserEntity> userEntityOpt = userEntityRepository.findById(user.getId());
        Optional<ParentArticleEntity> articleEntityOpt = parentArticleEntityRepository.findById(article.getId());

        return userEntityOpt.isPresent() &&
                articleEntityOpt.isPresent() &&
                articleEntityOpt.get().getAuthorId().equals(user.getId());
    }

    @Override
    public boolean canDelete(Question article, User user) {
        Optional<UserEntity> userEntityOpt = userEntityRepository.findById(user.getId());
        Optional<ParentArticleEntity> articleEntityOpt = parentArticleEntityRepository.findById(article.getId());

        return userEntityOpt.isPresent() &&
                articleEntityOpt.isPresent() &&
                articleEntityOpt.get().getAuthorId().equals(user.getId());
    }
}

// Inline Comments:
// - The `canWrite` method checks if the article ID is null (indicating it's a new article), and verifies if the user has a role that allows writing.
// - The `canUpdate` and `canDelete` methods ensure both the article and the user exist in the database and that the user is the author of the article.