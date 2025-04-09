package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Answer.class, DefaultAnswerReader.class, AnswerReader.class }
)
public class AnswerReaderTest {

    private IChildArticleEntityRepositoryFixture childArticleEntityRepositoryFixture;
    private IUserEntityRepositoryFixture userEntityRepositoryFixture;
    private DefaultAnswerReader answerReader;

    @BeforeEach
    void setup() {
        childArticleEntityRepositoryFixture = new IChildArticleEntityRepositoryFixture();
        userEntityRepositoryFixture = new IUserEntityRepositoryFixture();
        answerReader = new DefaultAnswerReader(childArticleEntityRepositoryFixture, userEntityRepositoryFixture);
    }

    @Test
    void read_shouldReturnAnswerWhenValidArticleId() {
        ChildArticleEntity entity = ChildArticleEntity.of(1L, 1L, ArticleType.ANSWER, "title", "content",
                new PopularityEmbeddable(10, 5, 1, 3), 1L, 1, false);
        childArticleEntityRepositoryFixture.save(entity);

        Answer result = answerReader.read(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("content", result.getContent());
    }

    @Test
    void read_shouldThrowExceptionWhenInvalidArticleId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerReader.read(2L);
        });

        assertEquals("Invalid article ID or not an answer type", exception.getMessage());
    }

    @Test
    void read_shouldThrowExceptionWhenNotAnswerType() {
        ChildArticleEntity entity = ChildArticleEntity.of(1L, 1L, ArticleType.QUESTION, "title", "content",
                new PopularityEmbeddable(10, 5, 1, 3), 1L, 1, false);
        childArticleEntityRepositoryFixture.save(entity);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerReader.read(1L);
        });

        assertEquals("Invalid article ID or not an answer type", exception.getMessage());
    }

    @Test
    void listByParentId_shouldReturnAnswersSortedByOrder() {
        ChildArticleEntity entity1 = ChildArticleEntity.of(1L, 1L, ArticleType.ANSWER, "title1", "content1",
                new PopularityEmbeddable(10, 5, 1, 3), 1L, 2, false);
        ChildArticleEntity entity2 = ChildArticleEntity.of(2L, 1L, ArticleType.ANSWER, "title2", "content2",
                new PopularityEmbeddable(15, 3, 2, 5), 1L, 1, false);
        childArticleEntityRepositoryFixture.save(entity1);
        childArticleEntityRepositoryFixture.save(entity2);

        Pageable pageable = PageRequest.of(0, 10);
        List<Answer> result = answerReader.listByParentId(1L, 0, 10);

        assertEquals(2, result.size());
        assertEquals("title2", result.get(0).getTitle());
        assertEquals("title1", result.get(1).getTitle());
    }

    @Test
    void listByParentId_shouldReturnEmptyListWhenNoAnswers() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Answer> result = answerReader.listByParentId(1L, 0, 10);

        assertTrue(result.isEmpty());
    }
}