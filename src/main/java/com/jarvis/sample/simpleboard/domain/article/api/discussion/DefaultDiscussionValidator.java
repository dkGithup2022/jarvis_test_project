package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { Discussion.class, DiscussionValidator.class, ArticleValidatorBase.class,
            UserEntity.class, IUserEntityRepository.class,
            ParentArticleEntity.class, IParentArticleEntityRepository.class,
            ArticleType.class, UserRole.class, Popularity.class }
)
@RequiredArgsConstructor
public class DefaultDiscussionValidator implements DiscussionValidator {

    private final IUserEntityRepository userEntityRepository;
    private final IParentArticleEntityRepository parentArticleEntityRepository;

    @Override
    public boolean canWrite(Discussion article, User user) {
        if (user.getUserRole() == null || user.getUserRole().isEmpty()) {
            return false;
        }
        return article.getId() == null;
    }

    @Override
    public boolean canUpdate(Discussion article, User user) {
        if (!article.getAuthorId().equals(user.getId())) {
            return false;
        }
        Optional<UserEntity> userEntity = userEntityRepository.findById(user.getId());
        Optional<ParentArticleEntity> articleEntity = parentArticleEntityRepository.findById(article.getId());
        return userEntity.isPresent() && articleEntity.isPresent();
    }

    @Override
    public boolean canDelete(Discussion article, User user) {
        if (!article.getAuthorId().equals(user.getId())) {
            return false;
        }
        Optional<UserEntity> userEntity = userEntityRepository.findById(user.getId());
        Optional<ParentArticleEntity> articleEntity = parentArticleEntityRepository.findById(article.getId());
        return userEntity.isPresent() && articleEntity.isPresent();
    }
}

// Note: The `User` class is not defined in the provided code. 
// Assuming it has at least the methods `getId()` and `getUserRole()`.