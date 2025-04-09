package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.PopularityMapper;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Announcement.class, DefaultAnnouncementReader.class, AnnouncementReader.class,
                ArticleReaderBase.class,

                UserEntity.class, IUserEntityRepositoryFixture.class,
                ArticleEntity.class, IArticleEntityRepositoryFixture.class,

                User.class, PopularityEmbeddable.class,
                UserRole.class,
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

    private void saveUser(IUserEntityRepositoryFixture fixture, Long id) {
        UserEntity user = UserEntity.of("passwordEncoded", "nickname", Set.of());
        FakeSetter.setField(user, "id", id);
        fixture.save(user);
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
        assertEquals(PopularityMapper.toRead(article.getPopularityEmbeddable()), result.getPopularity());
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
        ArticleEntity article = ArticleEntity.of(1L, 1L, ArticleType.ARTICLE, "Title", "Content", new PopularityEmbeddable(0, 0, 0, 0), false);
        articleFixture.save(article);

        Executable executable = () -> announcementReader.read(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Article is not of type ANNOUNCEMENT", exception.getMessage());
    }

    @Test
    void read_shouldThrowExceptionWhenAuthorDoesNotExist() {
        ArticleEntity article = ArticleEntity.of(1L, 1L, ArticleType.ANNOUNCEMENT, "Title", "Content", new PopularityEmbeddable(0, 0, 0, 0), false);
        articleFixture.save(article);

        Executable executable = () -> announcementReader.read(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("User not found for id: 1", exception.getMessage());
    }
}