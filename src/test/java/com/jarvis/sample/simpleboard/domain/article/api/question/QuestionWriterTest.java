package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import com.jarvis.sample.simpleboard.fixture.infra.article.parentArticle.IParentArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Question.class, DefaultQuestionWriter.class, QuestionWriter.class,
                ParentArticleEntity.class, IParentArticleEntityRepositoryFixture.class,
                ArticleType.class, Popularity.class
        }
)
public class QuestionWriterTest {

    private IParentArticleEntityRepositoryFixture fixture;
    private DefaultQuestionWriter questionWriter;

    @BeforeEach
    void setup() {
        fixture = new IParentArticleEntityRepositoryFixture();
        questionWriter = new DefaultQuestionWriter(fixture);
    }

    @Test
    void write_shouldSaveQuestion() {
        Question question = Question.of(null, ArticleType.QUESTION, "Title", "Content", 1L, null, Popularity.empty(), false);
        
        Question result = questionWriter.write(question);

        assertNotNull(result.getId());
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
        assertEquals(ArticleType.QUESTION, result.getArticleType());
        assertFalse(result.getDeleted());
    }

    @Test
    void write_shouldThrowExceptionWhenArticleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> questionWriter.write(null));
    }

    @Test
    void update_shouldUpdateExistingQuestion() {
        ParentArticleEntity existingEntity = fixture.save(ParentArticleEntity.of(
                ArticleType.QUESTION, "Old Title", "Old Content", 1L, null, false));

        Question updatedQuestion = Question.of(existingEntity.getId(), ArticleType.QUESTION, "New Title", "New Content", 1L, null, Popularity.empty(), false);

        Question result = questionWriter.update(updatedQuestion);

        assertEquals(existingEntity.getId(), result.getId());
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
    }

    @Test
    void update_shouldThrowExceptionWhenArticleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> questionWriter.update(null));
    }

    @Test
    void update_shouldThrowExceptionWhenArticleIdIsNull() {
        Question question = Question.of(null, ArticleType.QUESTION, "Title", "Content", 1L, null, Popularity.empty(), false);
        assertThrows(IllegalArgumentException.class, () -> questionWriter.update(question));
    }

    @Test
    void update_shouldThrowExceptionWhenArticleNotFound() {
        Question question = Question.of(999L, ArticleType.QUESTION, "Title", "Content", 1L, null, Popularity.empty(), false);
        assertThrows(RuntimeException.class, () -> questionWriter.update(question));
    }

    @Test
    void delete_shouldMarkArticleAsDeleted() {
        ParentArticleEntity existingEntity = fixture.save(ParentArticleEntity.of(
                ArticleType.QUESTION, "Title", "Content", 1L, null, false));

        questionWriter.delete(existingEntity.getId());

        Optional<ParentArticleEntity> deletedEntityOpt = fixture.findById(existingEntity.getId());
        assertTrue(deletedEntityOpt.isPresent());
        assertTrue(deletedEntityOpt.get().getDeleted());
    }

    @Test
    void delete_shouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> questionWriter.delete(null));
    }

    @Test
    void delete_shouldThrowExceptionWhenArticleNotFound() {
        assertThrows(RuntimeException.class, () -> questionWriter.delete(999L));
    }
}