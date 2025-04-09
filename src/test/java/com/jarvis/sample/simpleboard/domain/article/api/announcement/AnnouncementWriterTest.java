package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
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
                Announcement.class, DefaultAnnouncementWriter.class, AnnouncementWriter.class,
                ArticleWriterBase.class,
                UserEntity.class, IUserEntityRepositoryFixture.class, IUserEntityRepository.class,
                ArticleEntity.class, IArticleEntityRepositoryFixture.class, IArticleEntityRepository.class,
                User.class, PopularityEmbeddable.class,
                UserRole.class,
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
    void write_shouldSaveNewAnnouncement() {
        UserEntity author = UserEntity.of("encodedPassword", "AuthorNickname", Set.of(UserRole.USER));
        userRepositoryFixture.save(author);
        
        Announcement announcement = Announcement.of(null, author.getId(), null, "Title", "Content", Popularity.empty(), false);

        Announcement result = announcementWriter.write(announcement);

        assertNotNull(result.getId());
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
        assertEquals(author.getNickname(), result.getAuthorNickname());
    }

    @Test
    void write_shouldThrowExceptionWhenIdIsNotNull() {
        Announcement announcement = Announcement.of(1L, 1L, "AuthorNickname", "Title", "Content", Popularity.empty(), false);

        assertThrows(IllegalArgumentException.class, () -> announcementWriter.write(announcement));
    }

    @Test
    void write_shouldThrowExceptionWhenAuthorDoesNotExist() {
        Announcement announcement = Announcement.of(null, 999L, null, "Title", "Content", Popularity.empty(), false);

        assertThrows(IllegalArgumentException.class, () -> announcementWriter.write(announcement));
    }

    @Test
    void update_shouldUpdateExistingAnnouncement() {
        UserEntity author = UserEntity.of("encodedPassword", "AuthorNickname", Set.of(UserRole.USER));
        userRepositoryFixture.save(author);

        ArticleEntity existingEntity = ArticleEntity.of(author.getId(), ArticleType.ANNOUNCEMENT, "Old Title", "Old Content", null, false);
        articleRepositoryFixture.save(existingEntity);

        Announcement updatedAnnouncement = Announcement.of(existingEntity.getId(), author.getId(), null, "Updated Title", "Updated Content", Popularity.empty(), false);

        Announcement result = announcementWriter.update(updatedAnnouncement);

        assertEquals(existingEntity.getId(), result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
    }

    @Test
    void update_shouldThrowExceptionWhenIdIsNull() {
        Announcement announcement = Announcement.of(null, 1L, "AuthorNickname", "Title", "Content", Popularity.empty(), false);

        assertThrows(IllegalArgumentException.class, () -> announcementWriter.update(announcement));
    }

    @Test
    void update_shouldThrowExceptionWhenAnnouncementDoesNotExist() {
        Announcement announcement = Announcement.of(999L, 1L, "AuthorNickname", "Title", "Content", Popularity.empty(), false);

        assertThrows(IllegalArgumentException.class, () -> announcementWriter.update(announcement));
    }

    @Test
    void delete_shouldMarkAnnouncementAsDeleted() {
        UserEntity author = UserEntity.of("encodedPassword", "AuthorNickname", Set.of(UserRole.USER));
        userRepositoryFixture.save(author);

        ArticleEntity existingEntity = ArticleEntity.of(author.getId(), ArticleType.ANNOUNCEMENT, "Title", "Content", null, false);
        articleRepositoryFixture.save(existingEntity);

        announcementWriter.delete(existingEntity.getId());

        Optional<ArticleEntity> result = articleRepositoryFixture.findById(existingEntity.getId());
        assertTrue(result.isPresent());
        assertTrue(result.get().getDeleted());
    }

    @Test
    void delete_shouldThrowExceptionWhenAnnouncementDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> announcementWriter.delete(999L));
    }
}