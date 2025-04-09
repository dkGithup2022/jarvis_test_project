package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.comment.comment.ICommentEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {Comment.class, DefaultCommentValidator.class, CommentValidator.class,
                ICommentEntityRepositoryFixture.class,
                CommentEntity.class,
                ArticleType.class,
                UserEntity.class,
                User.class,
                UserRole.class
        }
)
public class CommentValidatorTest {

    private ICommentEntityRepositoryFixture commentFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultCommentValidator commentValidator;

    @BeforeEach
    void setup() {
        commentFixture = new ICommentEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        commentValidator = new DefaultCommentValidator(commentFixture, userFixture);
    }

    @Test
    void canWrite_shouldReturnTrue_whenUserHasValidRoleAndExistsInDb() {
        User user = User.of(1L, "validUser", Set.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        UserEntity userEntity = UserEntity.of("password", "validUser", Set.of(UserRole.USER));
        userFixture.save(userEntity);

        Boolean result = commentValidator.canWrite(user, comment);

        assertTrue(result);
    }

    @Test
    void canWrite_shouldReturnFalse_whenUserIsNull() {
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        Boolean result = commentValidator.canWrite(null, comment);

        assertFalse(result);
    }

    @Test
    void canWrite_shouldReturnFalse_whenUserRoleIsInvalid() {
        User user = User.of(1L, "invalidUser", Set.of());
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        UserEntity userEntity = UserEntity.of("password", "invalidUser", Set.of());
        userFixture.save(userEntity);

        Boolean result = commentValidator.canWrite(user, comment);

        assertFalse(result);
    }

    @Test
    void canWrite_shouldReturnFalse_whenUserDoesNotExistInDb() {
        User user = User.of(1L, "nonExistentUser", Set.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        Boolean result = commentValidator.canWrite(user, comment);

        assertFalse(result);
    }

    @Test
    void canUpdate_shouldReturnTrue_whenUserOwnsCommentAndBothExistInDb() {
        User user = User.of(1L, "validUser", Set.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        UserEntity userEntity = UserEntity.of("password", "validUser", Set.of(UserRole.USER));
        CommentEntity commentEntity = CommentEntity.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        userFixture.save(userEntity);
        commentFixture.save(commentEntity);

        Boolean result = commentValidator.canUpdate(user, comment);

        assertTrue(result);
    }

    @Test
    void canUpdate_shouldReturnFalse_whenUserIsNull() {
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        Boolean result = commentValidator.canUpdate(null, comment);

        assertFalse(result);
    }

    @Test
    void canUpdate_shouldReturnFalse_whenCommentIsNull() {
        User user = User.of(1L, "validUser", Set.of(UserRole.USER));

        Boolean result = commentValidator.canUpdate(user, null);

        assertFalse(result);
    }

    @Test
    void canUpdate_shouldReturnFalse_whenUserAndCommentDoNotExistInDb() {
        User user = User.of(1L, "nonExistentUser", Set.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        Boolean result = commentValidator.canUpdate(user, comment);

        assertFalse(result);
    }

    @Test
    void canUpdate_shouldReturnFalse_whenUserIdDoesNotMatchCommentOwnerId() {
        User user = User.of(2L, "validUser", Set.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        UserEntity userEntity = UserEntity.of("password", "validUser", Set.of(UserRole.USER));
        CommentEntity commentEntity = CommentEntity.of(1L, ArticleType.ARTICLE, 1L, "Valid content", null, 1, 1, 0, false);

        userFixture.save(userEntity);
        commentFixture.save(commentEntity);

        Boolean result = commentValidator.canUpdate(user, comment);

        assertFalse(result);
    }
}