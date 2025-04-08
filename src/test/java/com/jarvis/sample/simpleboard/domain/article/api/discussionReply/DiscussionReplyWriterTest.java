package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
}