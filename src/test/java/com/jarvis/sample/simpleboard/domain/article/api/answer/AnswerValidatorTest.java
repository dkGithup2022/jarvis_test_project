package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

public class AnswerValidatorTest {

    private IChildArticleEntityRepositoryFixture childArticleFixture;
    private IUserEntityRepositoryFixture userEntityFixture;
    private DefaultAnswerValidator answerValidator;

    private UserEntity saveUser(IUserEntityRepositoryFixture fixture, Long id) {
        UserEntity user = UserEntity.of("passwordEncoded", "nickname", Set.of(UserRole.USER));
        FakeSetter.setField(user, "id", 1L);
        return fixture.save(user);
    }

    private User getUserDomain(Long id) {
        var entity = userEntityFixture.findById(id).get();
        return User.of(entity.getId(), entity.getNickname(), entity.getUserRole());
    }

    @BeforeEach
    void setup() {
        childArticleFixture = new IChildArticleEntityRepositoryFixture();
        userEntityFixture = new IUserEntityRepositoryFixture();
        answerValidator = new DefaultAnswerValidator(childArticleFixture, userEntityFixture);
    }

    @Test
    void canWrite_whenUserAndAnswerValid_shouldReturnTrue() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(null, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canWrite(answer, getUserDomain(1L));

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
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canWrite(answer, getUserDomain(1L));

        assertFalse(result);
    }

    @Test
    void canUpdate_whenAnswerAndUserValid_shouldReturnTrue() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);
        childArticleFixture.save(ChildArticleEntity.of(1L, user.getId(), ArticleType.ANSWER, "title", "content", new PopularityEmbeddable(0, 0, 0, 0), null, 1, false));

        boolean result = answerValidator.canUpdate(answer, getUserDomain(1L));

        assertTrue(result);
    }

    @Test
    void canUpdate_whenAnswerOrUserNull_shouldReturnFalse() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canUpdate(null, getUserDomain(1L));

        assertFalse(result);

        result = answerValidator.canUpdate(answer, null);

        assertFalse(result);
    }

    @Test
    void canUpdate_whenArticleOrUserDoesNotExist_shouldReturnFalse() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canUpdate(answer, getUserDomain(1L));

        assertFalse(result);
    }

    @Test
    void canDelete_whenAnswerAndUserValid_shouldReturnTrue() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);
        childArticleFixture.save(ChildArticleEntity.of(1L, user.getId(), ArticleType.ANSWER, "title", "content", new PopularityEmbeddable(0, 0, 0, 0), null, 1, false));

        boolean result = answerValidator.canDelete(answer, getUserDomain(1L));

        assertTrue(result);
    }

    @Test
    void canDelete_whenAnswerOrUserNull_shouldReturnFalse() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canDelete(null, getUserDomain(1L));

        assertFalse(result);

        result = answerValidator.canDelete(answer, null);

        assertFalse(result);
    }

    @Test
    void canDelete_whenArticleOrUserDoesNotExist_shouldReturnFalse() {
        var user = saveUser(userEntityFixture, 1L);
        Answer answer = Answer.of(1L, user.getId(), user.getNickname(), "title", "content", Popularity.empty(), null, 1, false);

        boolean result = answerValidator.canDelete(answer, getUserDomain(1L));

        assertFalse(result);
    }
}