package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {Comment.class, CommentValidator.class

        }
)
@RequiredArgsConstructor
public class DefaultCommentValidator implements CommentValidator {

    private final ICommentEntityRepository commentEntityRepository;
    private final IUserEntityRepository userEntityRepository;

    @Override
    public Boolean canWrite(User user, Comment comment) {
        if (user == null) return false;

        EnumSet<UserRole> validRoles = EnumSet.of(UserRole.USER, UserRole.SYSTEM, UserRole.ADMIN);
        boolean hasValidRole = user.getUserRole().stream().anyMatch(validRoles::contains);

        boolean userExists = userEntityRepository.findById(user.getUserId()).isPresent();

        return hasValidRole && userExists;
    }

    @Override
    public Boolean canUpdate(User user, Comment comment) {
        if (user == null || comment == null || !user.getUserId().equals(comment.getId())) return false;

        boolean userExists = userEntityRepository.findById(user.getUserId()).isPresent();
        boolean commentExists = commentEntityRepository.findById(comment.getId()).isPresent();

        return userExists && commentExists;
    }
}

// Note: The User and UserRole classes, and their respective methods like getRoles(), getId(), are assumed to be defined elsewhere in the codebase.