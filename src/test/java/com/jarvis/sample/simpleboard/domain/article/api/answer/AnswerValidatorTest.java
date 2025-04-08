package com.jarvis.sample.simpleboard.domain.article.api.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;
import com.jarvis.sample.simpleboard.domain.article.specs.Popularity;
import com.jarvis.sample.simpleboard.domain.user.User;

public class AnswerValidatorTest {

    private IChildArticleEntityRepositoryFixture childArticleFixture;
    private IUserEntityRepositoryFixture userEntityFixture;
    private DefaultAnswerValidator answerValidator;

    @BeforeEach
    void setup() {
        childArticleFixture = new IChildArticleEntityRepositoryFixture();
        userEntityFixture = new IUserEntityRepositoryFixture();
        answerValidator = new DefaultAnswerValidator(childArticleFixture, userEntityFixture);
    }

    @Test
    void canWrite_whenUserAndAnswerValid_shouldReturnTrue() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(null, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canWrite(answer, user);

        assertTrue(result);
    }

    @Test
    void canWrite_whenUserNull_shouldReturnFalse() {
        Answer answer = Answer.of(null, 1L, "author", "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canWrite(answer, null);

        assertFalse(result);
    }

    @Test
    void canWrite_whenAnswerIdNotNull_shouldReturnFalse() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canWrite(answer, user);

        assertFalse(result);
    }

    @Test
    void canUpdate_whenAnswerAndUserValid_shouldReturnTrue() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);
        childArticleFixture.save(ChildArticleEntity.of(1L, user.getId(), ArticleType.ANSWER, "title", "content", new PopularityEmbeddable(0, 0, 0, 0), null, 1, false));

        boolean result = answerValidator.canUpdate(answer, user);

        assertTrue(result);
    }

    @Test
    void canUpdate_whenAnswerOrUserNull_shouldReturnFalse() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canUpdate(null, user);

        assertFalse(result);

        result = answerValidator.canUpdate(answer, null);

        assertFalse(result);
    }

    @Test
    void canUpdate_whenArticleOrUserDoesNotExist_shouldReturnFalse() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canUpdate(answer, user);

        assertFalse(result);
    }

    @Test
    void canDelete_whenAnswerAndUserValid_shouldReturnTrue() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);
        childArticleFixture.save(ChildArticleEntity.of(1L, user.getId(), ArticleType.ANSWER, "title", "content", new PopularityEmbeddable(0, 0, 0, 0), null, 1, false));

        boolean result = answerValidator.canDelete(answer, user);

        assertTrue(result);
    }

    @Test
    void canDelete_whenAnswerOrUserNull_shouldReturnFalse() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canDelete(null, user);

        assertFalse(result);

        result = answerValidator.canDelete(answer, null);

        assertFalse(result);
    }

    @Test
    void canDelete_whenArticleOrUserDoesNotExist_shouldReturnFalse() {
        User user = new User(1L, "testUser", "ROLE_USER");
        Answer answer = Answer.of(1L, user.getId(), user.getUsername(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canDelete(answer, user);

        assertFalse(result);
    }
}