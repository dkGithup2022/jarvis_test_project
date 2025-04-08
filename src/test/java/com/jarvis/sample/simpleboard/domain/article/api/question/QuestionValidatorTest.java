package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class QuestionValidatorTest {

    private IUserEntityRepository userEntityRepository;
    private IParentArticleEntityRepository parentArticleEntityRepository;
    private DefaultQuestionValidator questionValidator;
    private User user;

    @BeforeEach
    void setup() {
        userEntityRepository = Mockito.mock(IUserEntityRepository.class);
        parentArticleEntityRepository = Mockito.mock(IParentArticleEntityRepository.class);
        questionValidator = new DefaultQuestionValidator(parentArticleEntityRepository, userEntityRepository);
        user = new User(1L); // Simplified User class for testing
    }

    @Test
    void canWrite_shouldReturnTrueForNewArticleWithValidUserRole() {
        Question newQuestion = Question.of(null, null, "title", "content", 1L, "nickname", null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        when(userEntityRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));

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

        when(userEntityRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(parentArticleEntityRepository.findById(question.getId())).thenReturn(Optional.of(articleEntity));

        assertTrue(questionValidator.canUpdate(question, user));
    }

    @Test
    void canUpdate_shouldReturnFalseWhenUserIsNotAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 2L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        when(userEntityRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(parentArticleEntityRepository.findById(question.getId())).thenReturn(Optional.of(articleEntity));

        assertFalse(questionValidator.canUpdate(question, user));
    }

    @Test
    void canDelete_shouldReturnTrueWhenUserIsAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 1L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        when(userEntityRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(parentArticleEntityRepository.findById(question.getId())).thenReturn(Optional.of(articleEntity));

        assertTrue(questionValidator.canDelete(question, user));
    }

    @Test
    void canDelete_shouldReturnFalseWhenUserIsNotAuthor() {
        Question question = Question.of(1L, null, "title", "content", 1L, "nickname", null, false);
        ParentArticleEntity articleEntity = ParentArticleEntity.of(1L, null, "title", "content", 2L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        when(userEntityRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(parentArticleEntityRepository.findById(question.getId())).thenReturn(Optional.of(articleEntity));

        assertFalse(questionValidator.canDelete(question, user));
    }
}

// Note: The User class used in this test is a simplified version for testing purposes only.