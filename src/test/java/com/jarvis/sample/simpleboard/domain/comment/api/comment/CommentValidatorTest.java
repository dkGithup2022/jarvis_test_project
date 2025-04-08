package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.comment.impl.DefaultCommentValidator;
import com.jarvis.sample.simpleboard.domain.comment.specs.CommentValidator;
import com.jarvis.sample.simpleboard.domain.user.User;
import com.jarvis.sample.simpleboard.domain.user.UserRole;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Comment.class, DefaultCommentValidator.class, CommentValidator.class }
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
    void canWrite_shouldReturnFalseIfUserIsNull() {
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        assertFalse(commentValidator.canWrite(null, comment));
    }

    @Test
    void canWrite_shouldReturnFalseIfUserRoleIsInvalid() {
        User user = new User(1L, List.of(UserRole.GUEST)); // Assuming UserRole.GUEST is an invalid role
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        userFixture.save(user);
        assertFalse(commentValidator.canWrite(user, comment));
    }

    @Test
    void canWrite_shouldReturnFalseIfUserDoesNotExist() {
        User user = new User(1L, List.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        assertFalse(commentValidator.canWrite(user, comment));
    }

    @Test
    void canWrite_shouldReturnTrueIfUserHasValidRoleAndExists() {
        User user = new User(1L, List.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        userFixture.save(user);
        assertTrue(commentValidator.canWrite(user, comment));
    }

    @Test
    void canUpdate_shouldReturnFalseIfUserIsNull() {
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        assertFalse(commentValidator.canUpdate(null, comment));
    }

    @Test
    void canUpdate_shouldReturnFalseIfCommentIsNull() {
        User user = new User(1L, List.of(UserRole.USER));
        userFixture.save(user);
        assertFalse(commentValidator.canUpdate(user, null));
    }

    @Test
    void canUpdate_shouldReturnFalseIfUserAndCommentIdsDoNotMatch() {
        User user = new User(1L, List.of(UserRole.USER));
        Comment comment = Comment.of(2L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        userFixture.save(user);
        commentFixture.save(comment);
        assertFalse(commentValidator.canUpdate(user, comment));
    }

    @Test
    void canUpdate_shouldReturnFalseIfUserDoesNotExist() {
        User user = new User(1L, List.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        commentFixture.save(comment);
        assertFalse(commentValidator.canUpdate(user, comment));
    }

    @Test
    void canUpdate_shouldReturnFalseIfCommentDoesNotExist() {
        User user = new User(1L, List.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        userFixture.save(user);
        assertFalse(commentValidator.canUpdate(user, comment));
    }

    @Test
    void canUpdate_shouldReturnTrueIfUserAndCommentExistAndIdsMatch() {
        User user = new User(1L, List.of(UserRole.USER));
        Comment comment = Comment.of(1L, ArticleType.NORMAL, 1L, "This is a comment", null, 1, 1, 0, false);
        userFixture.save(user);
        commentFixture.save(comment);
        assertTrue(commentValidator.canUpdate(user, comment));
    }
}

// Note: The User class and UserRole enum are assumed to be available in the codebase and used for user-related operations.