package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Article;

import java.util.Optional;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = {
        Article.class, ArticleValidator.class,
        ArticleValidatorBase.class,
        UserEntity.class, IUserEntityRepository.class,
        ArticleEntity.class, IArticleEntityRepository.class,
        ArticleType.class, Popularity.class
    }
)
public class DefaultArticleValidator implements ArticleValidator {

    private final IArticleEntityRepository articleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    public DefaultArticleValidator(IArticleEntityRepository articleEntityRepository, IUserEntityRepository userEntityRepository) {
        this.articleEntityRepository = articleEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public boolean canWrite(Article article, User user) {
        if (user.getUserRole() == null || user.getUserRole().isEmpty()) {
            return false;
        }
        // If article id is specified, it cannot be used for writing
        if (article.getId() != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canUpdate(Article article, User user) {
        if (article.getAuthorId().equals(user.getUserId())) {
            Optional<ArticleEntity> articleEntity = articleEntityRepository.findById(article.getId());
            Optional<UserEntity> userEntity = userEntityRepository.findById(user.getUserId());
            return articleEntity.isPresent() && userEntity.isPresent();
        }
        return false;
    }

    @Override
    public boolean canDelete(Article article, User user) {
        if (article.getAuthorId().equals(user.getUserId())) {
            Optional<ArticleEntity> articleEntity = articleEntityRepository.findById(article.getId());
            Optional<UserEntity> userEntity = userEntityRepository.findById(user.getUserId());
            return articleEntity.isPresent() && userEntity.isPresent();
        }
        return false;
    }
}

// Inline comments:
// - The `canWrite` method checks if the `user` has roles and that the `article` does not have an ID for creation.
// - The `canUpdate` and `canDelete` methods ensure the `article` author matches the `user` and verify the existence of both the `article` and `user` in the database.