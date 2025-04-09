package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
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
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Discussion.class,
                DefaultDiscussionReader.class,
                DiscussionReader.class,
                UserEntity.class,
                IUserEntityRepositoryFixture.class,
                ParentArticleEntity.class,
                IParentArticleEntityRepositoryFixture.class,
                User.class,
                PopularityEmbeddable.class,
                ArticleType.class,
                UserRole.class,
                Popularity.class
        }
)
public class DiscussionReaderTest {

    private IParentArticleEntityRepositoryFixture parentArticleRepoFixture;
    private IUserEntityRepositoryFixture userRepoFixture;
    private DefaultDiscussionReader discussionReader;

    @BeforeEach
    void setUp() {
        parentArticleRepoFixture = new IParentArticleEntityRepositoryFixture();
        userRepoFixture = new IUserEntityRepositoryFixture();
        discussionReader = new DefaultDiscussionReader(parentArticleRepoFixture, userRepoFixture);
    }

    @Test
    void read_shouldReturnDiscussion_whenArticleExists() {
        Long articleId = 1L;
        Long authorId = 1L;
        ParentArticleEntity articleEntity = ParentArticleEntity.of(articleId, ArticleType.DISCUSSION, "title", "content", authorId, new PopularityEmbeddable(10, 5, 0, 3), false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));
        FakeSetter.setField(userEntity, "id", authorId);
        parentArticleRepoFixture.save(articleEntity);
        userRepoFixture.save(userEntity);

        Discussion discussion = discussionReader.read(articleId);

        assertNotNull(discussion);
        assertEquals(articleId, discussion.getId());
        assertEquals(authorId, discussion.getAuthorId());
        assertEquals("nickname", discussion.getAuthorNickname());
    }

    @Test
    void read_shouldThrowException_whenArticleNotFound() {
        Long articleId = 1L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            discussionReader.read(articleId);
        });

        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void read_shouldThrowException_whenArticleTypeIsInvalid() {
        Long articleId = 1L;
        ParentArticleEntity articleEntity = ParentArticleEntity.of(articleId, ArticleType.ARTICLE, "title", "content", 1L, new PopularityEmbeddable(10, 5, 0, 3), false);

        parentArticleRepoFixture.save(articleEntity);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            discussionReader.read(articleId);
        });

        assertEquals("Invalid article type", exception.getMessage());
    }

    @Test
    void read_shouldThrowException_whenUserNotFound() {
        Long articleId = 1L;
        ParentArticleEntity articleEntity = ParentArticleEntity.of(articleId, ArticleType.DISCUSSION, "title", "content", 1L, new PopularityEmbeddable(10, 5, 0, 3), false);

        parentArticleRepoFixture.save(articleEntity);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            discussionReader.read(articleId);
        });

        assertEquals("User not found", exception.getMessage());
    }
}