package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {DiscussionReply.class, DiscussionReplyValidator.class,
                ArticleValidatorBase.class,
                ChildArticleEntity.class,
                IChildArticleEntityRepository.class,
                Popularity.class,
                PopularityEmbeddable.class,
                IUserEntityRepository.class,
                ArticleType.class
        }
)
@Component
public class DefaultDiscussionReplyValidator implements DiscussionReplyValidator {

    private final IChildArticleEntityRepository childArticleEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    public DefaultDiscussionReplyValidator(IChildArticleEntityRepository childArticleEntityRepository, IUserEntityRepository userEntityRepository) {
        this.childArticleEntityRepository = childArticleEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public boolean canWrite(DiscussionReply article, User user) {
        return user != null && user.getUserRole() != null && !user.getUserRole().isEmpty() && article.getId() == null;
    }

    @Override
    public boolean canUpdate(DiscussionReply article, User user) {
        if (article == null || user == null) {
            return false;
        }
        
        if (!article.getAuthorId().equals(user.getUserId())) {
            return false;
        }

        Optional<ChildArticleEntity> articleEntity = childArticleEntityRepository.findById(article.getId());
        Optional<UserEntity> userEntity = userEntityRepository.findById(user.getUserId());

        return articleEntity.isPresent() && userEntity.isPresent();
    }

    @Override
    public boolean canDelete(DiscussionReply article, User user) {
        return canUpdate(article, user);
    }
}