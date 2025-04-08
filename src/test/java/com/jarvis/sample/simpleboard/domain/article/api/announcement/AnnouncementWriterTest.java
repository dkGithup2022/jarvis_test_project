package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Announcement.class, DefaultAnnouncementWriter.class, AnnouncementWriter.class,
            ArticleWriterBase.class,
            UserEntity.class, IUserEntityRepositoryFixture.class,
            ArticleEntity.class, IArticleEntityRepositoryFixture.class,
            ArticleType.class, Popularity.class
    }
)
public class AnnouncementWriterTest {

    private IArticleEntityRepositoryFixture articleRepositoryFixture;
    private IUserEntityRepositoryFixture userRepositoryFixture;
    private DefaultAnnouncementWriter announcementWriter;

    @BeforeEach
    void setup() {
        articleRepositoryFixture = new IArticleEntityRepositoryFixture();
        userRepositoryFixture = new IUserEntityRepositoryFixture();
        announcementWriter = new DefaultAnnouncementWriter(articleRepositoryFixture, userRepositoryFixture);
    }

    @Test
    void write_shouldCreateNewAnnouncementWhenValid() {
        UserEntity author = UserEntity.of("passwordEncoded", "Author", Set.of());
        Long authorId = 1L;
        FakeSetter.setField(author, "id", authorId);
        userRepositoryFixture.getDb().put(authorId, author);

        Announcement announcement = Announcement.of(null, authorId, "Author", "Title", "Content", Popularity.empty(), false);

        Announcement result = announcementWriter.write(announcement);

        assertNotNull(result.getId());
        assertEquals(authorId, result.getAuthorId());
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
    }

    @Test
    void write_shouldThrowExceptionWhenAnnouncementHasId() {
        Announcement announcement = Announcement.of(1L, 1L, "Author", "Title", "Content", Popularity.empty(), false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            announcementWriter.write(announcement);
        });

        assertEquals("New announcement must not have an ID.", exception.getMessage());
    }

    @Test
    void write_shouldThrowExceptionWhenAuthorDoesNotExist() {
        Announcement announcement = Announcement.of(null, 1L, "Author", "Title", "Content", Popularity.empty(), false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            announcementWriter.write(announcement);
        });

        assertEquals("Author does not exist.", exception.getMessage());
    }

    @Test
    void update_shouldUpdateExistingAnnouncement() {
        UserEntity author = UserEntity.of("passwordEncoded", "Author", Set.of());
        Long authorId = 1L;
        FakeSetter.setField(author, "id", authorId);
        userRepositoryFixture.getDb().put(authorId, author);

        ArticleEntity articleEntity = ArticleEntity.of(authorId, ArticleType.ANNOUNCEMENT, "Old Title", "Old Content", null, false);
        Long articleId = 1L;
        FakeSetter.setField(articleEntity, "id", articleId);
        articleRepositoryFixture.getDb().put(articleId, articleEntity);

        Announcement announcement = Announcement.of(articleId, authorId, "Author", "New Title", "New Content", Popularity.empty(), false);

        Announcement result = announcementWriter.update(announcement);

        assertEquals(articleId, result.getId());
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
    }

    @Test
    void update_shouldThrowExceptionWhenAnnouncementHasNoId() {
        Announcement announcement = Announcement.of(null, 1L, "Author", "Title", "Content", Popularity.empty(), false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            announcementWriter.update(announcement);
        });

        assertEquals("Announcement must have an ID to be updated.", exception.getMessage());
    }

    @Test
    void update_shouldThrowExceptionWhenAnnouncementDoesNotExist() {
        Announcement announcement = Announcement.of(1L, 1L, "Author", "Title", "Content", Popularity.empty(), false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            announcementWriter.update(announcement);
        });

        assertEquals("Announcement does not exist.", exception.getMessage());
    }

    @Test
    void delete_shouldMarkAnnouncementAsDeleted() {
        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.ANNOUNCEMENT, "Title", "Content", null, false);
        Long articleId = 1L;
        FakeSetter.setField(articleEntity, "id", articleId);
        articleRepositoryFixture.getDb().put(articleId, articleEntity);

        announcementWriter.delete(articleId);

        Optional<ArticleEntity> result = articleRepositoryFixture.findById(articleId);
        assertTrue(result.isPresent());
        assertTrue(result.get().getDeleted());
    }

    @Test
    void delete_shouldThrowExceptionWhenAnnouncementDoesNotExist() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            announcementWriter.delete(1L);
        });

        assertEquals("Announcement does not exist.", exception.getMessage());
    }
}