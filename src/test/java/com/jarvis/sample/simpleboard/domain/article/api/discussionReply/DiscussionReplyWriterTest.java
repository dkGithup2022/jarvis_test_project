package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
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

public class DiscussionReplyWriterTest {

    private IChildArticleEntityRepositoryFixture childArticleFixture;
    private IUserEntityRepositoryFixture userEntityFixture;
    private DefaultDiscussionReplyWriter discussionReplyWriter;

    @BeforeEach
    void setUp() {
        childArticleFixture = new IChildArticleEntityRepositoryFixture();
        userEntityFixture = new IUserEntityRepositoryFixture();
        discussionReplyWriter = new DefaultDiscussionReplyWriter(childArticleFixture, userEntityFixture);
    }

    @Test
    void write_shouldSaveArticleWithoutId() {
        DiscussionReply reply = DiscussionReply.of(null, 1L, "Author", "Title", "Content",
                Popularity.empty(), 1L, 0, false);

        DiscussionReply result = discussionReplyWriter.write(reply);

        assertNotNull(result.getId());
        assertEquals(reply.getTitle(), result.getTitle());
        assertEquals(reply.getContent(), result.getContent());
    }

    @Test
    void write_shouldThrowExceptionForArticleWithId() {
        DiscussionReply reply = DiscussionReply.of(1L, 1L, "Author", "Title", "Content",
                Popularity.empty(), 1L, 0, false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            discussionReplyWriter.write(reply);
        });

        assertEquals("Article ID must not be present when writing a new article", thrown.getMessage());
    }

    @Test
    void update_shouldUpdateExistingArticle() {
        ChildArticleEntity existingEntity = ChildArticleEntity.of(1L, 1L, ArticleType.DISCUSSION_REPLY, "Old Title", "Old Content",
                new PopularityEmbeddable(0, 0, 0, 0), 1L, 0, false);
        childArticleFixture.save(existingEntity);

        DiscussionReply reply = DiscussionReply.of(1L, 1L, "Author", "New Title", "New Content",
                Popularity.empty(), 1L, 0, false);

        DiscussionReply result = discussionReplyWriter.update(reply);

        assertEquals(reply.getTitle(), result.getTitle());
        assertEquals(reply.getContent(), result.getContent());
    }

    @Test
    void update_shouldThrowExceptionForNonExistingArticle() {
        DiscussionReply reply = DiscussionReply.of(1L, 1L, "Author", "Title", "Content",
                Popularity.empty(), 1L, 0, false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            discussionReplyWriter.update(reply);
        });

        assertEquals("Article does not exist", thrown.getMessage());
    }

    @Test
    void update_shouldThrowExceptionForArticleWithoutId() {
        DiscussionReply reply = DiscussionReply.of(null, 1L, "Author", "Title", "Content",
                Popularity.empty(), 1L, 0, false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            discussionReplyWriter.update(reply);
        });

        assertEquals("Article ID must be present when updating an article", thrown.getMessage());
    }

    @Test
    void delete_shouldMarkArticleAsDeleted() {
        ChildArticleEntity existingEntity = ChildArticleEntity.of(1L, 1L, ArticleType.DISCUSSION_REPLY, "Title", "Content",
                new PopularityEmbeddable(0, 0, 0, 0), 1L, 0, false);
        childArticleFixture.save(existingEntity);

        discussionReplyWriter.delete(1L);

        Optional<ChildArticleEntity> result = childArticleFixture.findById(1L);
        assertTrue(result.isPresent());
        assertTrue(result.get().getDeleted());
    }

    @Test
    void delete_shouldThrowExceptionForNonExistingArticle() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            discussionReplyWriter.delete(1L);
        });

        assertEquals("Article does not exist", thrown.getMessage());
    }
}