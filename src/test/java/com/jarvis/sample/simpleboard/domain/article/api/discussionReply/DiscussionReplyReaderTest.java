package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
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
        references = {DiscussionReply.class, DefaultDiscussionReplyReader.class,
                DiscussionReplyReader.class,
                ArticleReaderBase.class,
                ChildArticleEntity.class,
                IChildArticleEntityRepository.class,
                IChildArticleEntityRepositoryFixture.class,
                Popularity.class,
                PopularityEmbeddable.class,
                IUserEntityRepository.class,
                IUserEntityRepositoryFixture.class,
                ArticleType.class
        }
)
public class DiscussionReplyReaderTest {

    private IChildArticleEntityRepositoryFixture articleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultDiscussionReplyReader discussionReplyReader;

    @BeforeEach
    void setup() {
        articleFixture = new IChildArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        discussionReplyReader = new DefaultDiscussionReplyReader(articleFixture, userFixture);
    }

    @Test
    void read_shouldReturnDiscussionReply_whenArticleExistsAndIsNotDeleted() {
        UserEntity userEntity = UserEntity.of("pw", "authorNickname", Set.of(UserRole.USER));
        FakeSetter.setField(userEntity, "id", 1L);
        userFixture.save(userEntity);

        ChildArticleEntity articleEntity = ChildArticleEntity.of(
                1L, userEntity.getId(), ArticleType.DISCUSSION_REPLY, "Test Title", "Test Content",
                new PopularityEmbeddable(10, 5, 0, 2), 1L, 1, false);
        articleFixture.save(articleEntity);

        DiscussionReply result = discussionReplyReader.read(articleEntity.getId());

        assertNotNull(result);
        assertEquals(articleEntity.getId(), result.getId());
        assertEquals(articleEntity.getContent(), result.getContent());
        assertEquals(articleEntity.getTitle(), result.getTitle());
        assertEquals(userEntity.getNickname(), result.getAuthorNickname());
    }

    @Test
    void read_shouldThrowException_whenArticleDoesNotExist() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            discussionReplyReader.read(999L);
        });
        assertEquals("Article not found or has been deleted.", exception.getMessage());
    }

    @Test
    void read_shouldThrowException_whenArticleIsDeleted() {
        ChildArticleEntity articleEntity = ChildArticleEntity.of(
                1L, 1L, ArticleType.DISCUSSION_REPLY, "Test Title", "Test Content",
                new PopularityEmbeddable(10, 5, 0, 2), 1L, 1, true);
        articleFixture.save(articleEntity);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            discussionReplyReader.read(articleEntity.getId());
        });
        assertEquals("Article not found or has been deleted.", exception.getMessage());
    }

    @Test
    void read_shouldThrowException_whenArticleTypeIsIncorrect() {
        ChildArticleEntity articleEntity = ChildArticleEntity.of(
                1L, 1L, ArticleType.ANNOUNCEMENT, "Test Title", "Test Content",
                new PopularityEmbeddable(10, 5, 0, 2), 1L, 1, false);
        articleFixture.save(articleEntity);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            discussionReplyReader.read(articleEntity.getId());
        });
        assertEquals("Incorrect article type for Discussion Reply.", exception.getMessage());
    }

    @Test
    void read_shouldReturnUnknownAuthorNickname_whenUserDoesNotExist() {
        ChildArticleEntity articleEntity = ChildArticleEntity.of(
                1L, 999L, ArticleType.DISCUSSION_REPLY, "Test Title", "Test Content",
                new PopularityEmbeddable(10, 5, 0, 2), 1L, 1, false);
        articleFixture.save(articleEntity);

        DiscussionReply result = discussionReplyReader.read(articleEntity.getId());

        assertEquals("Unknown", result.getAuthorNickname());
    }
}