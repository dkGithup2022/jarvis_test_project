package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Announcement.class, DefaultAnnouncementReader.class, AnnouncementReader.class,

            ArticleReaderBase.class,
            UserEntity.class, IUserEntityRepositoryFixture.class,
            ArticleEntity.class, IArticleEntityRepositoryFixture.class,
            ArticleType.class, Popularity.class
    }
)
public class AnnouncementReaderTest {

    private IArticleEntityRepositoryFixture articleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultAnnouncementReader announcementReader;

    @BeforeEach
    void setup() {
        articleFixture = new IArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        announcementReader = new DefaultAnnouncementReader(articleFixture, userFixture);
    }

    @Test
    void read_shouldReturnAnnouncementWhenArticleExistsAndIsAnnouncementType() {
        UserEntity user = UserEntity.of("passwordEncoded", "nickname", Set.of());
        FakeSetter.setField(user, "id", 1L);
        
        ArticleEntity article = ArticleEntity.of(1L, 1L, ArticleType.ANNOUNCEMENT, "Title", "Content", new PopularityEmbeddable(), false);
        articleFixture.save(article);
        userFixture.save(user);

        Announcement result = announcementReader.read(1L);

        assertNotNull(result);
        assertEquals(article.getId(), result.getId());
        assertEquals(article.getAuthorId(), result.getAuthorId());
        assertEquals(user.getNickname(), result.getAuthorNickname());
        assertEquals(article.getTitle(), result.getTitle());
        assertEquals(article.getContent(), result.getContent());
        assertEquals(article.getPopularityEmbeddable().toPopularity(), result.getPopularity());
        assertEquals(article.getDeleted(), result.getDeleted());
    }

    @Test
    void read_shouldThrowExceptionWhenArticleDoesNotExist() {
        Executable executable = () -> announcementReader.read(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Article not found for id: 1", exception.getMessage());
    }

    @Test
    void read_shouldThrowExceptionWhenArticleIsNotAnnouncementType() {
        ArticleEntity article = ArticleEntity.of(1L, 1L, ArticleType.NORMAL, "Title", "Content", new PopularityEmbeddable(), false);
        articleFixture.save(article);

        Executable executable = () -> announcementReader.read(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Article is not of type ANNOUNCEMENT", exception.getMessage());
    }

    @Test
    void read_shouldThrowExceptionWhenAuthorDoesNotExist() {
        ArticleEntity article = ArticleEntity.of(1L, 1L, ArticleType.ANNOUNCEMENT, "Title", "Content", new PopularityEmbeddable(), false);
        articleFixture.save(article);

        Executable executable = () -> announcementReader.read(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("User not found for id: 1", exception.getMessage());
    }
}