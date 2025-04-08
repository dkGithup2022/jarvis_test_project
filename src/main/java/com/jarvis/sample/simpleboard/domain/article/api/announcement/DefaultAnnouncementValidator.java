package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleValidator;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.domain.user.User;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { Announcement.class,  AnnouncementValidator.class,
            ArticleValidatorBase.class,
            UserEntity.class, IUserEntityRepository.class,
            ArticleEntity.class, IArticleEntityRepository.class,
            ArticleType.class }
)
@RequiredArgsConstructor
public class DefaultAnnouncementValidator implements AnnouncementValidator {
    
    private final IUserEntityRepository userEntityRepository;
    private final IArticleEntityRepository articleEntityRepository;

    @Override
    public boolean canWrite(Announcement article, User user) {
        return user.getUserRoles() != null && !user.getUserRoles().isEmpty() && article.getId() == null;
    }

    @Override
    public boolean canUpdate(Announcement article, User user) {
        return articleExists(article.getId()) && userExists(user.getId()) && article.getAuthorId().equals(user.getId());
    }

    @Override
    public boolean canDelete(Announcement article, User user) {
        return articleExists(article.getId()) && userExists(user.getId()) && article.getAuthorId().equals(user.getId());
    }

    private boolean articleExists(Long articleId) {
        return articleId != null && articleEntityRepository.findById(articleId).isPresent();
    }

    private boolean userExists(Long userId) {
        return userId != null && userEntityRepository.findById(userId).isPresent();
    }
}