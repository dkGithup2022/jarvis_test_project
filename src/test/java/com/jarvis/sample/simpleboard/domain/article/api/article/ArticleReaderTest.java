package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Article.class, DefaultArticleReader.class, ArticleReader.class,
                ArticleReaderBase.class,

                UserEntity.class, IUserEntityRepositoryFixture.class,
                ArticleEntity.class, IArticleEntityRepositoryFixture.class,

                User.class, PopularityEmbeddable.class,
                UserRole.class,
                ArticleType.class, Popularity.class
        }
)
public class ArticleReaderTest {

    private IArticleEntityRepositoryFixture articleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultArticleReader articleReader;

    @BeforeEach
    void setup() {
        articleFixture = new IArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        articleReader = new DefaultArticleReader(articleFixture, userFixture);
    }

    private UserEntity saveUser(IUserEntityRepositoryFixture fixture, Long id) {
        UserEntity user = UserEntity.of("passwordEncoded", "nickname", Set.of(UserRole.USER));
        FakeSetter.setField(user, "id", id);
        return fixture.save(user);
    }

    private User getUserDomain(Long id) {
        var entity = userFixture.findById(id).get();
        return User.of(entity.getId(), entity.getNickname(), entity.getUserRole());
    }

    @Test
    void read_shouldReturnArticleWithAuthorNickname() {
        Long articleId = 1L;
        Long authorId = 2L;
        ArticleEntity articleEntity = ArticleEntity.of(articleId, authorId, ArticleType.ARTICLE, "Title", "Content", new PopularityEmbeddable(10, 5, 2, 3), false);

        articleFixture.save(articleEntity);
        var user = saveUser(userFixture, authorId);

        Article result = articleReader.read(articleId);

        assertNotNull(result);
        assertEquals(articleId, result.getId());
        assertEquals(authorId, result.getAuthorId());
        assertEquals(user.getNickname(), result.getAuthorNickname());
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
        assertEquals(10, result.getPopularity().views());
    }

    @Test
    void read_shouldThrowExceptionWhenArticleNotFound() {
        Long nonExistingArticleId = 99L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> articleReader.read(nonExistingArticleId));

        assertEquals("Article not found for id: " + nonExistingArticleId, exception.getMessage());
    }
}