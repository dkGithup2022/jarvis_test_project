package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscussionWriterTest {

    private IParentArticleEntityRepository parentArticleEntityRepository;
    private IUserEntityRepository userEntityRepository;
    private DefaultDiscussionWriter discussionWriter;

    @BeforeEach
    void setup() {
        parentArticleEntityRepository = mock(IParentArticleEntityRepository.class);
        userEntityRepository = mock(IUserEntityRepository.class);
        discussionWriter = new DefaultDiscussionWriter(parentArticleEntityRepository, userEntityRepository);
    }

    @Test
    void write_shouldSaveNewDiscussion() {
        UserEntity author = UserEntity.of("encodedPassword", "authorNickname", Set.of(UserRole.USER));
        Discussion article = Discussion.of(null, 1L, "authorNickname", "Test Title", "Test Content", Popularity.empty(), false);

        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(author));
        when(parentArticleEntityRepository.save(any(ParentArticleEntity.class)))
                .thenAnswer(invocation -> {
                    ParentArticleEntity entity = invocation.getArgument(0);
                    return ParentArticleEntity.of(1L, entity.getArticleType(), entity.getTitle(), entity.getContent(),
                            entity.getAuthorId(), entity.getPopularityEmbeddable(), entity.getDeleted());
                });

        Discussion savedDiscussion = discussionWriter.write(article);

        assertNotNull(savedDiscussion);
        assertEquals("Test Title", savedDiscussion.title());
        assertEquals("Test Content", savedDiscussion.content());
        assertEquals("authorNickname", savedDiscussion.authorNickname());
        verify(parentArticleEntityRepository, times(1)).save(any(ParentArticleEntity.class));
    }

    @Test
    void write_shouldThrowExceptionIfArticleIdIsNotNull() {
        Discussion article = Discussion.of(1L, 1L, "authorNickname", "Test Title", "Test Content", Popularity.empty(), false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.write(article));

        assertEquals("Article ID must be null for a new article", exception.getMessage());
    }

    @Test
    void write_shouldThrowExceptionIfAuthorNotFound() {
        Discussion article = Discussion.of(null, 1L, "authorNickname", "Test Title", "Test Content", Popularity.empty(), false);

        when(userEntityRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.write(article));

        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void update_shouldUpdateExistingDiscussion() {
        ParentArticleEntity existingEntity = ParentArticleEntity.of(1L, ArticleType.DISCUSSION, "Old Title", "Old Content", 1L, null, false);
        UserEntity author = UserEntity.of("encodedPassword", "authorNickname", Set.of(UserRole.USER));
        Discussion article = Discussion.of(1L, 1L, "authorNickname", "Updated Title", "Updated Content", Popularity.empty(), false);

        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(author));
        when(parentArticleEntityRepository.save(any(ParentArticleEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Discussion updatedDiscussion = discussionWriter.update(article);

        assertNotNull(updatedDiscussion);
        assertEquals("Updated Title", updatedDiscussion.title());
        assertEquals("Updated Content", updatedDiscussion.content());
        verify(parentArticleEntityRepository, times(1)).save(any(ParentArticleEntity.class));
    }

    @Test
    void update_shouldThrowExceptionIfArticleNotFound() {
        Discussion article = Discussion.of(1L, 1L, "authorNickname", "Updated Title", "Updated Content", Popularity.empty(), false);

        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.update(article));

        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void delete_shouldSetDeletedFlag() {
        ParentArticleEntity existingEntity = ParentArticleEntity.of(1L, ArticleType.DISCUSSION, "Title", "Content", 1L, null, false);

        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(parentArticleEntityRepository.save(any(ParentArticleEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        discussionWriter.delete(1L);

        verify(parentArticleEntityRepository, times(1)).save(argThat(entity -> entity.getDeleted()));
    }

    @Test
    void delete_shouldThrowExceptionIfArticleNotFound() {
        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.delete(1L));

        assertEquals("Article not found", exception.getMessage());
    }
}