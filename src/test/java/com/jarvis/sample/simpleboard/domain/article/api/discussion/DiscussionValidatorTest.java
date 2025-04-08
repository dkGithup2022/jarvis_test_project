package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscussionValidatorTest {

    private IUserEntityRepository userEntityRepository;
    private IParentArticleEntityRepository parentArticleEntityRepository;
    private DefaultDiscussionValidator discussionValidator;

    @BeforeEach
    void setUp() {
        userEntityRepository = mock(IUserEntityRepository.class);
        parentArticleEntityRepository = mock(IParentArticleEntityRepository.class);
        discussionValidator = new DefaultDiscussionValidator(userEntityRepository, parentArticleEntityRepository);
    }

    @Test
    void canWrite_ShouldReturnFalse_WhenUserRoleIsNull() {
        Discussion article = Discussion.of(null, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, null);
        boolean result = discussionValidator.canWrite(article, user);
        assertFalse(result);
    }

    @Test
    void canWrite_ShouldReturnFalse_WhenArticleIdIsNotNull() {
        Discussion article = Discussion.of(1L, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));
        boolean result = discussionValidator.canWrite(article, user);
        assertFalse(result);
    }

    @Test
    void canWrite_ShouldReturnTrue_WhenUserRoleIsNotEmptyAndArticleIdIsNull() {
        Discussion article = Discussion.of(null, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));
        boolean result = discussionValidator.canWrite(article, user);
        assertTrue(result);
    }

    @Test
    void canUpdate_ShouldReturnFalse_WhenAuthorIdDoesNotMatchUserId() {
        Discussion article = Discussion.of(1L, 2L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));
        boolean result = discussionValidator.canUpdate(article, user);
        assertFalse(result);
    }

    @Test
    void canUpdate_ShouldReturnFalse_WhenUserEntityOrArticleEntityDoesNotExist() {
        Discussion article = Discussion.of(1L, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));

        when(userEntityRepository.findById(1L)).thenReturn(Optional.empty());
        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = discussionValidator.canUpdate(article, user);
        assertFalse(result);
    }

    @Test
    void canUpdate_ShouldReturnTrue_WhenUserEntityAndArticleEntityExistAndAuthorIdMatchesUserId() {
        Discussion article = Discussion.of(1L, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));

        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(mock(ParentArticleEntity.class)));

        boolean result = discussionValidator.canUpdate(article, user);
        assertTrue(result);
    }

    @Test
    void canDelete_ShouldReturnFalse_WhenAuthorIdDoesNotMatchUserId() {
        Discussion article = Discussion.of(1L, 2L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));
        boolean result = discussionValidator.canDelete(article, user);
        assertFalse(result);
    }

    @Test
    void canDelete_ShouldReturnFalse_WhenUserEntityOrArticleEntityDoesNotExist() {
        Discussion article = Discussion.of(1L, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));

        when(userEntityRepository.findById(1L)).thenReturn(Optional.empty());
        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = discussionValidator.canDelete(article, user);
        assertFalse(result);
    }

    @Test
    void canDelete_ShouldReturnTrue_WhenUserEntityAndArticleEntityExistAndAuthorIdMatchesUserId() {
        Discussion article = Discussion.of(1L, 1L, "author", "title", "content", Popularity.empty(), false);
        User user = new User(1L, Collections.singleton(UserRole.USER));

        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(mock(ParentArticleEntity.class)));

        boolean result = discussionValidator.canDelete(article, user);
        assertTrue(result);
    }

    // Assuming User class exists with methods getId() and getUserRole()
    private static class User {
        private final Long id;
        private final Set<UserRole> userRoles;

        public User(Long id, Set<UserRole> userRoles) {
            this.id = id;
            this.userRoles = userRoles;
        }

        public Long getId() {
            return id;
        }

        public Set<UserRole> getUserRole() {
            return userRoles;
        }
    }
}