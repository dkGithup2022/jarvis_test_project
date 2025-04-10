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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;
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
                ArticleType.class, Popularity.class, PopularityMapper.class
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
    @DisplayName("read() 함수에 대한 성공 케이스 - ANNOUNCEMENT 타입의 문서 읽기 성공")
    void read_shouldReturnAnnouncement_whenArticleIsOfTypeAnnouncement() {
        UserEntity author = UserEntity.of("encodedPassword", "authorNickname", Set.of(UserRole.USER));
        userFixture.save(author);

        ArticleEntity article = ArticleEntity.of(author.getId(), ArticleType.ANNOUNCEMENT, "Title", "Content", new PopularityEmbeddable(1, 2, 3, 4), false);
        articleFixture.save(article);

        Announcement result = announcementReader.read(article.getId());

        assertNotNull(result);
        assertEquals(article.getId(), result.getId());
        assertEquals(author.getNickname(), result.getAuthorNickname());
    }

    @Test
    @DisplayName("read() 함수에 대한 실패 케이스 - 문서 ID로 문서를 찾을 수 없는 경우 예외 발생")
    void read_shouldThrowException_whenArticleIdNotFound() {
        long nonExistentArticleId = 999L;

        Executable executable = () -> announcementReader.read(nonExistentArticleId);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Article not found for id: " + nonExistentArticleId, exception.getMessage());
    }

    @Test
    @DisplayName("read() 함수에 대한 실패 케이스 - 문서 타입이 ANNOUNCEMENT가 아닌 경우 예외 발생")
    void read_shouldThrowException_whenArticleIsNotOfTypeAnnouncement() {
        UserEntity author = UserEntity.of("encodedPassword", "authorNickname", Set.of(UserRole.USER));
        userFixture.save(author);

        ArticleEntity article = ArticleEntity.of(author.getId(), ArticleType.ARTICLE, "Title", "Content", new PopularityEmbeddable(1, 2, 3, 4), false);
        articleFixture.save(article);

        Executable executable = () -> announcementReader.read(article.getId());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Article is not of type ANNOUNCEMENT", exception.getMessage());
    }

    @Test
    @DisplayName("read() 함수에 대한 실패 케이스 - 작성자 ID로 사용자를 찾을 수 없는 경우 예외 발생")
    void read_shouldThrowException_whenAuthorIdNotFound() {
        ArticleEntity article = ArticleEntity.of(999L, ArticleType.ANNOUNCEMENT, "Title", "Content", new PopularityEmbeddable(1, 2, 3, 4), false);
        articleFixture.save(article);

        Executable executable = () -> announcementReader.read(article.getId());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("User not found for id: " + article.getAuthorId(), exception.getMessage());
    }
}