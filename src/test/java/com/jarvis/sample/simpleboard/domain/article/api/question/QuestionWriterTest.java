package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {Question.class, DefaultQuestionWriter.class, QuestionWriter.class,
                UserEntity.class, IUserEntityRepository.class,
                ParentArticleEntity.class, IParentArticleEntityRepository.class,
                ArticleType.class, UserRole.class,
                Popularity.class}
)
public class QuestionWriterTest {

    private IParentArticleEntityRepository repository;
    private DefaultQuestionWriter questionWriter;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IParentArticleEntityRepository.class);
        questionWriter = new DefaultQuestionWriter(repository);
    }

    @Test
    void write_shouldPersistQuestionSuccessfully() {
        Question question = new Question(null, "Title", "Content", 1L, null);
        ParentArticleEntity entity = ParentArticleEntity.of(ArticleType.QUESTION, "Title", "Content", 1L, null, false);
        ParentArticleEntity savedEntity = ParentArticleEntity.of(1L, ArticleType.QUESTION, "Title", "Content", 1L, null, false);

        when(repository.save(any(ParentArticleEntity.class))).thenReturn(savedEntity);

        Question result = questionWriter.write(question);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
        verify(repository, times(1)).save(any(ParentArticleEntity.class));
    }

    @Test
    void write_shouldThrowExceptionWhenArticleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> questionWriter.write(null));
    }

    @Test
    void update_shouldUpdateQuestionSuccessfully() {
        Question question = new Question(1L, "Updated Title", "Updated Content", 1L, null);
        ParentArticleEntity existingEntity = ParentArticleEntity.of(1L, ArticleType.QUESTION, "Old Title", "Old Content", 1L, null, false);
        ParentArticleEntity updatedEntity = ParentArticleEntity.of(1L, ArticleType.QUESTION, "Updated Title", "Updated Content", 1L, null, false);

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(ParentArticleEntity.class))).thenReturn(updatedEntity);

        Question result = questionWriter.update(question);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(ParentArticleEntity.class));
    }

    @Test
    void update_shouldThrowExceptionWhenArticleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> questionWriter.update(null));
    }

    @Test
    void update_shouldThrowExceptionWhenArticleIdIsNull() {
        Question question = new Question(null, "Title", "Content", 1L, null);
        assertThrows(IllegalArgumentException.class, () -> questionWriter.update(question));
    }

    @Test
    void update_shouldThrowExceptionWhenArticleNotFound() {
        Question question = new Question(99L, "Title", "Content", 1L, null);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> questionWriter.update(question));
    }

    @Test
    void delete_shouldMarkArticleAsDeleted() {
        ParentArticleEntity existingEntity = ParentArticleEntity.of(1L, ArticleType.QUESTION, "Title", "Content", 1L, null, false);
        ParentArticleEntity deletedEntity = ParentArticleEntity.of(1L, ArticleType.QUESTION, "Title", "Content", 1L, null, true);

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(ParentArticleEntity.class))).thenReturn(deletedEntity);

        questionWriter.delete(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(ParentArticleEntity.class));
    }

    @Test
    void delete_shouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> questionWriter.delete(null));
    }

    @Test
    void delete_shouldThrowExceptionWhenArticleNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> questionWriter.delete(99L));
    }
}