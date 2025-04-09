package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AnnouncementValidatorTest {

    private IUserEntityRepositoryFixture userRepositoryFixture;
    private IArticleEntityRepositoryFixture articleRepositoryFixture;
    private DefaultAnnouncementValidator announcementValidator;

    @BeforeEach
    void setup() {
        userRepositoryFixture = new IUserEntityRepositoryFixture();
        articleRepositoryFixture = new IArticleEntityRepositoryFixture();
        announcementValidator = new DefaultAnnouncementValidator(userRepositoryFixture, articleRepositoryFixture);
    }

    @Test
    void canWrite_shouldReturnTrue_whenUserHasRolesAndArticleIsNew() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        Announcement article = Announcement.of(null, null, "nickname", "title", "content", Popularity.empty(), false);

        assertTrue(announcementValidator.canWrite(article, user_));
    }

    @Test
    void canWrite_shouldReturnFalse_whenUserHasNoRoles() {
        UserEntity user = UserEntity.of("password", "nickname", Collections.emptySet());
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        Announcement article = Announcement.of(null, null, "nickname", "title", "content", Popularity.empty(), false);

        assertFalse(announcementValidator.canWrite(article, user_));
    }

    @Test
    void canWrite_shouldReturnFalse_whenArticleIsNotNew() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        Announcement article = Announcement.of(1L, null, "nickname", "title", "content", Popularity.empty(), false);

        assertFalse(announcementValidator.canWrite(article, user_));
    }

    @Test
    void canUpdate_shouldReturnTrue_whenArticleExistsAndUserExistsAndAuthorMatches() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        Long userId = 1L;
        FakeSetter.setField(user, "id", userId);
        userRepositoryFixture.save(user);
        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        ArticleEntity articleEntity = ArticleEntity.of(1L, userId, ArticleType.ANNOUNCEMENT, "title", "content", null, false);
        articleRepositoryFixture.save(articleEntity);

        Announcement article = Announcement.of(articleEntity.getId(), userId, "nickname", "title", "content", Popularity.empty(), false);

        assertTrue(announcementValidator.canUpdate(article, user_));
    }

    @Test
    void canUpdate_shouldReturnFalse_whenArticleDoesNotExist() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        Long userId = 1L;
        FakeSetter.setField(user, "id", userId);

        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());
        userRepositoryFixture.save(user);

        Announcement article = Announcement.of(99L, userId, "nickname", "title", "content", Popularity.empty(), false);

        assertFalse(announcementValidator.canUpdate(article, user_));
    }

    @Test
    void canUpdate_shouldReturnFalse_whenUserDoesNotExist() {
        Long userId = 1L;
        ArticleEntity articleEntity = ArticleEntity.of(1L, userId, ArticleType.ANNOUNCEMENT, "title", "content", null, false);
        articleRepositoryFixture.save(articleEntity);


        Announcement article = Announcement.of(articleEntity.getId(), userId, "nickname", "title", "content", Popularity.empty(), false);
        User user = User.of(null, "nickname", Set.of(UserRole.ADMIN));
        FakeSetter.setField(user, "id", 99L);

        assertFalse(announcementValidator.canUpdate(article, user));
    }

    @Test
    void canDelete_shouldReturnTrue_whenArticleExistsAndUserExistsAndAuthorMatches() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        Long userId = 1L;
        FakeSetter.setField(user, "id", userId);
        userRepositoryFixture.save(user);

        ArticleEntity articleEntity = ArticleEntity.of(1L, userId, ArticleType.ANNOUNCEMENT, "title", "content", null, false);
        articleRepositoryFixture.save(articleEntity);

        Announcement article = Announcement.of(articleEntity.getId(), userId, "nickname", "title", "content", Popularity.empty(), false);


        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        assertTrue(announcementValidator.canDelete(article, user_));
    }

    @Test
    void canDelete_shouldReturnFalse_whenArticleDoesNotExist() {
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        Long userId = 1L;
        FakeSetter.setField(user, "id", userId);
        userRepositoryFixture.save(user);

        Announcement article = Announcement.of(99L, userId, "nickname", "title", "content", Popularity.empty(), false);

        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        assertFalse(announcementValidator.canDelete(article, user_));
    }

    @Test
    void canDelete_shouldReturnFalse_whenUserDoesNotExist() {
        Long userId = 1L;
        ArticleEntity articleEntity = ArticleEntity.of(1L, userId, ArticleType.ANNOUNCEMENT, "title", "content", null, false);
        articleRepositoryFixture.save(articleEntity);

        Announcement article = Announcement.of(articleEntity.getId(), userId, "nickname", "title", "content", Popularity.empty(), false);
        UserEntity user = UserEntity.of("password", "nickname", Set.of(UserRole.ADMIN));
        FakeSetter.setField(user, "id", 99L);

        user = userRepositoryFixture.save(user);
        var user_ = User.of(user.getId(), user.getNickname(), user.getUserRole());

        assertFalse(announcementValidator.canDelete(article, user_));
    }
}