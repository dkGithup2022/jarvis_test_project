package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.parentArticle.IParentArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionValidatorTest {

    private IUserEntityRepositoryFixture userEntityRepository;
    private IParentArticleEntityRepositoryFixture parentArticleEntityRepository;
    private DefaultQuestionValidator questionValidator;
    private User user;

    @BeforeEach
    void setup() {
        userEntityRepository = new IUserEntityRepositoryFixture();
        parentArticleEntityRepository = new IParentArticleEntityRepositoryFixture();
        questionValidator = new DefaultQuestionValidator(parentArticleEntityRepository, userEntityRepository);
        user = User.of(1L, "nickname", Set.of(UserRole.USER));
    }

    @Test
    void canWrite_shouldReturnTrueForNewArticleWithValidUserRole() {
        Question newQuestion = Question.of(null, null, "title", "content", 1L, "nickname", null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        userEntityRepository.save(userEntity);

        assertTrue(questionValidator.canWrite(newQuestion, user));
    }

    @Test
    void canWrite_shouldReturnFalseForExistingArticle() {
        Question existingQuestion = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);

        assertFalse(questionValidator.canWrite(existingQuestion, user));
    }

    @Test
    void canUpdate_shouldReturnTrueWhenUserIsAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 1L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        userEntityRepository.save(userEntity);
        parentArticleEntityRepository.save(articleEntity);

        assertTrue(questionValidator.canUpdate(question, user));
    }

    @Test
    void canUpdate_shouldReturnFalseWhenUserIsNotAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 2L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        userEntityRepository.save(userEntity);
        parentArticleEntityRepository.save(articleEntity);

        assertFalse(questionValidator.canUpdate(question, user));
    }

    @Test
    void canDelete_shouldReturnTrueWhenUserIsAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 1L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        userEntityRepository.save(userEntity);
        parentArticleEntityRepository.save(articleEntity);

        assertTrue(questionValidator.canDelete(question, user));
    }

    @Test
    void canDelete_shouldReturnFalseWhenUserIsNotAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 2L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        userEntityRepository.save(userEntity);
        parentArticleEntityRepository.save(articleEntity);

        assertFalse(questionValidator.canDelete(question, user));
    }
}

// Note: The User class used in this test is a simplified version for testing purposes only.