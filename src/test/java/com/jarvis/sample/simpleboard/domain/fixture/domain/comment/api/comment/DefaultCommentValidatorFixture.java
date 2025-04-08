package com.jarvis.sample.simpleboard.domain.fixture.comment.api.comment;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.comment.specs.CommentValidator;
import com.jarvis.sample.simpleboard.domain.comment.impl.DefaultCommentValidator;
import com.jarvis.sample.simpleboard.domain.fixture.comment.repository.ICommentEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.domain.fixture.user.repository.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Comment.class, DefaultCommentValidator.class, CommentValidator.class }
)
public class DefaultCommentValidatorFixture implements CommentValidator {

    private final CommentValidator delegate;

    public DefaultCommentValidatorFixture() {
        this.delegate = new DefaultCommentValidator(
            new ICommentEntityRepositoryFixture(),
            new IUserEntityRepositoryFixture()
        );
    }

    @Override
    public Boolean canWrite(User user, Comment comment) {
        return delegate.canWrite(user, comment);
    }

    @Override
    public Boolean canUpdate(User user, Comment comment) {
        return delegate.canUpdate(user, comment);
    }
}

// Note: IUserEntityRepositoryFixture is assumed to implement IUserEntityRepository interface.