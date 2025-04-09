package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.Set;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Answer.class, DefaultAnswerWriter.class,
                AnswerWriter.class, ArticleWriterBase.class,
                ChildArticleEntity.class, IChildArticleEntityRepositoryFixture.class,
                IChildArticleEntityRepositoryFixture.class,
                User.class,
                Popularity.class,
                PopularityEmbeddable.class, IUserEntityRepository.class,
                IUserEntityRepositoryFixture.class, ArticleType.class
        }
)
public class AnswerWriterTest {

    private IChildArticleEntityRepositoryFixture articleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultAnswerWriter answerWriter;
    private User testUser;
    private UserEntity testUserEntity;

    @BeforeEach
    void setup() {
        articleFixture = new IChildArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        answerWriter = new DefaultAnswerWriter(articleFixture, userFixture);

        testUser = User.of(1L, "testUser", Set.of());
        testUserEntity = new UserEntity();
        FakeSetter.setField(testUserEntity, "id", 1L);
        FakeSetter.setField(testUserEntity, "nickname", "testUser");
        userFixture.save(testUserEntity);
    }

    @Test
    void write_shouldSaveNewAnswer() {
        Answer answer = Answer.of(null, 1L, "testUser", "Title", "Content", Popularity.empty(), 2L, 1, false);

        Answer savedAnswer = answerWriter.write(answer);

        assertNotNull(savedAnswer);
        assertNotNull(savedAnswer.getId());
        assertEquals("Title", savedAnswer.getTitle());
        assertEquals("Content", savedAnswer.getContent());
    }

    @Test
    void write_shouldFailIfUserDoesNotExist() {
        Answer answer = Answer.of(null, 999L, "nonExistentUser", "Title", "Content", Popularity.empty(), 2L, 1, false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> answerWriter.write(answer));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void update_shouldUpdateExistingAnswer() {
        Answer answer = Answer.of(null, 1L, "testUser", "Title", "Content", Popularity.empty(), 2L, 1, false);
        Answer savedAnswer = answerWriter.write(answer);

        Answer updatedAnswer = Answer.of(savedAnswer.getId(), 1L, "testUser", "Updated Title", "Updated Content", Popularity.empty(), 2L, 1, false);

        Answer result = answerWriter.update(updatedAnswer);

        assertEquals(savedAnswer.getId(), result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
    }

    @Test
    void update_shouldFailIfAnswerDoesNotExist() {
        Answer answer = Answer.of(999L, 1L, "testUser", "Title", "Content", Popularity.empty(), 2L, 1, false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> answerWriter.update(answer));

        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void delete_shouldMarkAnswerAsDeleted() {
        Answer answer = Answer.of(null, 1L, "testUser", "Title", "Content", Popularity.empty(), 2L, 1, false);
        Answer savedAnswer = answerWriter.write(answer);

        answerWriter.delete(savedAnswer.getId());

        ChildArticleEntity entity = articleFixture.findById(savedAnswer.getId()).orElseThrow();
        assertTrue(entity.getDeleted());
    }

    @Test
    void delete_shouldFailIfAnswerDoesNotExist() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> answerWriter.delete(999L));

        assertEquals("Article not found", exception.getMessage());
    }
}

// Note: FakeSetter is assumed to be a utility class available for setting fields in test scenarios.
// If FakeSetter is not available, consider using reflection or another approach to set fields as needed.