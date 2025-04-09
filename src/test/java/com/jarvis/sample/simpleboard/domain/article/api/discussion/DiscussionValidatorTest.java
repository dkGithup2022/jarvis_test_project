package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.parentArticle.IParentArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DiscussionValidatorTest {

    private IUserEntityRepositoryFixture userRepositoryFixture;
    private IParentArticleEntityRepositoryFixture articleRepositoryFixture;
    private DefaultDiscussionValidator discussionValidator;

    @BeforeEach
    void setup() {
        userRepositoryFixture = new IUserEntityRepositoryFixture();
        articleRepositoryFixture = new IParentArticleEntityRepositoryFixture();
        discussionValidator = new DefaultDiscussionValidator(userRepositoryFixture, articleRepositoryFixture);
    }

    @Test
    void canWrite_shouldReturnTrue_whenUserHasRolesAndArticleIsNew() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        Discussion article = Discussion.of(null, null, "nickname", "title", "content", null, false);

        assertTrue(discussionValidator.canWrite(article, user_));
    }

    @Test
    void canWrite_shouldReturnFalse_whenUserHasNoRoles() {
        UserEntity user = UserEntity.of("password", "nickname", Collections.emptySet());
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        Discussion article = Discussion.of(null, null, "nickname", "title", "content", null, false);

        assertFalse(discussionValidator.canWrite(article, user_));
    }

    @Test
    void canWrite_shouldReturnFalse_whenArticleIsNotNew() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        Discussion article = Discussion.of(1L, null, "nickname", "title", "content", null, false);

        assertFalse(discussionValidator.canWrite(article, user_));
    }

    @Test
    void canUpdate_shouldReturnTrue_whenArticleExistsAndUserExistsAndAuthorMatches() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        Long userId = 1L;
        userRepositoryFixture.save(user);
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", userId, null, false);
        articleRepositoryFixture.save(articleEntity);

        Discussion article = Discussion.of(articleEntity.getId(), userId, "nickname", "title", "content", null, false);

        assertTrue(discussionValidator.canUpdate(article, user_));
    }

    @Test
    void canUpdate_shouldReturnFalse_whenArticleDoesNotExist() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        Long userId = 1L;
        userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        Discussion article = Discussion.of(99L, userId, "nickname", "title", "content", null, false);

        assertFalse(discussionValidator.canUpdate(article, user_));
    }

    @Test
    void canUpdate_shouldReturnFalse_whenUserDoesNotExist() {
        Long userId = 1L;
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", userId, null, false);
        articleRepositoryFixture.save(articleEntity);

        Discussion article = Discussion.of(articleEntity.getId(), userId, "nickname", "title", "content", null, false);
        User user = User.of(null, "nickname", Set.of(UserRole.USER));

        assertFalse(discussionValidator.canUpdate(article, user));
    }

    @Test
    void canDelete_shouldReturnTrue_whenArticleExistsAndUserExistsAndAuthorMatches() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        Long userId = 1L;
        userRepositoryFixture.save(user);

        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", userId, null, false);
        articleRepositoryFixture.save(articleEntity);

        Discussion article = Discussion.of(articleEntity.getId(), userId, "nickname", "title", "content", null, false);

        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        assertTrue(discussionValidator.canDelete(article, user_));
    }

    @Test
    void canDelete_shouldReturnFalse_whenArticleDoesNotExist() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        Long userId = 1L;
        userRepositoryFixture.save(user);

        Discussion article = Discussion.of(99L, userId, "nickname", "title", "content", null, false);

        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        assertFalse(discussionValidator.canDelete(article, user_));
    }

    @Test
    void canDelete_shouldReturnFalse_whenUserDoesNotExist() {
        Long userId = 1L;
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", userId, null, false);
        articleRepositoryFixture.save(articleEntity);

        Discussion article = Discussion.of(articleEntity.getId(), userId, "nickname", "title", "content", null, false);
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        assertFalse(discussionValidator.canDelete(article, user_));
    }
}