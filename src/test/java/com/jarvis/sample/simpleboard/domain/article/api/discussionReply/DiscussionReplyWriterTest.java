package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = { DiscussionReply.class, DefaultDiscussionReplyWriter.class,
                DiscussionReplyWriter.class, ArticleWriterBase.class,
                ChildArticleEntity.class, IChildArticleEntityRepository.class,
                IChildArticleEntityRepositoryFixture.class, Popularity.class,
                PopularityEmbeddable.class, IUserEntityRepository.class,
                IUserEntityRepositoryFixture.class, ArticleType.class
        }
)
public class DiscussionReplyWriterTest {

    private IChildArticleEntityRepositoryFixture childArticleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultDiscussionReplyWriter discussionReplyWriter;

    @BeforeEach
    void setup() {
        childArticleFixture = new IChildArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        discussionReplyWriter = new DefaultDiscussionReplyWriter(childArticleFixture, userFixture);
    }

    @Test
    void write_shouldSaveReplySuccessfully_whenAuthorExists() {
        Long authorId = 1L;
        userFixture.getDb().put(authorId, new UserEntity(authorId, "authorName"));

        DiscussionReply reply = DiscussionReply.of(
                null,
                authorId,
                "authorName",
                "Sample Title",
                "Sample Content",
                Popularity.empty(),
                123L,
                1,
                false
        );

        DiscussionReply result = discussionReplyWriter.write(reply);

        assertNotNull(result.getId());
        assertEquals(reply.getTitle(), result.getTitle());
        assertEquals(reply.getContent(), result.getContent());
    }

    @Test
    void write_shouldThrowException_whenAuthorDoesNotExist() {
        DiscussionReply reply = DiscussionReply.of(
                null,
                2L,
                "nonExistentAuthor",
                "Sample Title",
                "Sample Content",
                Popularity.empty(),
                123L,
                1,
                false
        );

        assertThrows(IllegalArgumentException.class, () -> discussionReplyWriter.write(reply));
    }

    @Test
    void write_shouldThrowException_whenContentIsNullOrEmpty() {
        Long authorId = 1L;
        userFixture.getDb().put(authorId, new UserEntity(authorId, "authorName"));

        assertThrows(IllegalArgumentException.class, () -> {
            DiscussionReply reply = DiscussionReply.of(
                    null,
                    authorId,
                    "authorName",
                    "Sample Title",
                    "",
                    Popularity.empty(),
                    123L,
                    1,
                    false
            );
            discussionReplyWriter.write(reply);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DiscussionReply reply = DiscussionReply.of(
                    null,
                    authorId,
                    "authorName",
                    "Sample Title",
                    null,
                    Popularity.empty(),
                    123L,
                    1,
                    false
            );
            discussionReplyWriter.write(reply);
        });
    }

    @Test
    void write_shouldThrowException_whenContentExceedsMaxLength() {
        Long authorId = 1L;
        userFixture.getDb().put(authorId, new UserEntity(authorId, "authorName"));

        String longContent = "a".repeat(10_001);

        assertThrows(IllegalArgumentException.class, () -> {
            DiscussionReply reply = DiscussionReply.of(
                    null,
                    authorId,
                    "authorName",
                    "Sample Title",
                    longContent,
                    Popularity.empty(),
                    123L,
                    1,
                    false
            );
            discussionReplyWriter.write(reply);
        });
    }

    @Test
    void update_shouldUpdateReplySuccessfully_whenArticleExists() {
        Long authorId = 1L;
        userFixture.getDb().put(authorId, new UserEntity(authorId, "authorName"));
        ChildArticleEntity savedEntity = childArticleFixture.save(ChildArticleEntity.of(
                authorId, ArticleType.DISCUSSION_REPLY, "Old Title", "Old Content",
                new PopularityEmbeddable(0, 0, 0, 0), 123L, 1, false
        ));

        DiscussionReply updatedReply = DiscussionReply.of(
                savedEntity.getId(), authorId, "authorName", "New Title", "New Content",
                Popularity.empty(), 123L, 1, false
        );

        DiscussionReply result = discussionReplyWriter.update(updatedReply);

        assertEquals(updatedReply.getTitle(), result.getTitle());
        assertEquals(updatedReply.getContent(), result.getContent());
    }

    @Test
    void update_shouldThrowException_whenArticleDoesNotExist() {
        DiscussionReply reply = DiscussionReply.of(
                999L, 1L, "authorName", "Sample Title", "Sample Content",
                Popularity.empty(), 123L, 1, false
        );

        assertThrows(RuntimeException.class, () -> discussionReplyWriter.update(reply));
    }

    @Test
    void delete_shouldMarkReplyAsDeleted_whenArticleExists() {
        Long authorId = 1L;
        userFixture.getDb().put(authorId, new UserEntity(authorId, "authorName"));
        ChildArticleEntity savedEntity = childArticleFixture.save(ChildArticleEntity.of(
                authorId, ArticleType.DISCUSSION_REPLY, "Sample Title", "Sample Content",
                new PopularityEmbeddable(0, 0, 0, 0), 123L, 1, false
        ));

        discussionReplyWriter.delete(savedEntity.getId());

        Optional<ChildArticleEntity> deletedEntity = childArticleFixture.findById(savedEntity.getId());
        assertTrue(deletedEntity.isPresent());
        assertTrue(deletedEntity.get().getDeleted());
    }

    @Test
    void delete_shouldThrowException_whenArticleDoesNotExist() {
        assertThrows(RuntimeException.class, () -> discussionReplyWriter.delete(999L));
    }
}