package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.parentArticle.IParentArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Discussion.class, DefaultDiscussionWriter.class, DiscussionWriter.class,
                ArticleWriterBase.class,

                UserEntity.class, IUserEntityRepositoryFixture.class,
                ParentArticleEntity.class, IParentArticleEntityRepositoryFixture.class,

                User.class, PopularityEmbeddable.class,
                UserRole.class,
                ArticleType.class, Popularity.class
        }
)
public class DiscussionWriterTest {

    private IParentArticleEntityRepositoryFixture parentArticleFixture;
    private IUserEntityRepositoryFixture userEntityFixture;
    private DefaultDiscussionWriter discussionWriter;

    @BeforeEach
    void setup() {
        parentArticleFixture = new IParentArticleEntityRepositoryFixture();
        userEntityFixture = new IUserEntityRepositoryFixture();
        discussionWriter = new DefaultDiscussionWriter(parentArticleFixture, userEntityFixture);
    }

    @Test
    void write_shouldCreateNewDiscussion() {
        UserEntity author = UserEntity.of("password123", "authorNickname", Set.of(UserRole.USER));
        userEntityFixture.save(author);

        Discussion article = Discussion.of(null, author.getId(), "authorNickname", "Title", "Content", Popularity.empty(), false);
        Discussion result = discussionWriter.write(article);

        assertNotNull(result.getId());
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
        assertEquals(author.getId(), result.getAuthorId());
    }

    @Test
    void write_shouldFailIfArticleIdIsNotNull() {
        Discussion article = Discussion.of(1L, 1L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.write(article));
        assertEquals("Article ID must be null for a new article", exception.getMessage());
    }

    @Test
    void write_shouldFailIfAuthorNotFound() {
        Discussion article = Discussion.of(null, 999L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.write(article));
        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void update_shouldUpdateExistingDiscussion() {
        UserEntity author = UserEntity.of("password123", "authorNickname", Set.of(UserRole.USER));
        userEntityFixture.save(author);

        ParentArticleEntity entity = ParentArticleEntity.of(ArticleType.DISCUSSION, "Old Title", "Old Content", author.getId(), new PopularityEmbeddable(0, 0, 0, 0), false);
        parentArticleFixture.save(entity);

        Discussion article = Discussion.of(entity.getId(), author.getId(), "authorNickname", "New Title", "New Content", Popularity.empty(), false);
        Discussion result = discussionWriter.update(article);

        assertEquals(entity.getId(), result.getId());
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
    }

    @Test
    void update_shouldFailIfArticleNotFound() {
        Discussion article = Discussion.of(999L, 1L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.update(article));
        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void delete_shouldMarkDiscussionAsDeleted() {
        UserEntity author = UserEntity.of("password123", "authorNickname", Set.of(UserRole.USER));
        userEntityFixture.save(author);

        ParentArticleEntity entity = ParentArticleEntity.of(ArticleType.DISCUSSION, "Title", "Content", author.getId(), new PopularityEmbeddable(0, 0, 0, 0), false);
        parentArticleFixture.save(entity);

        discussionWriter.delete(entity.getId());

        ParentArticleEntity updatedEntity = parentArticleFixture.findById(entity.getId()).orElseThrow();
        assertTrue(updatedEntity.getDeleted());
    }

    @Test
    void delete_shouldFailIfArticleNotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> discussionWriter.delete(999L));
        assertEquals("Article not found", exception.getMessage());
    }
}

// Note: FakeSetter is assumed to be a utility class that allows setting private fields for testing purposes.