package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.domain.article.specs.Article;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = {Article.class, DefaultArticleValidator.class, ArticleValidator.class,
            ArticleValidatorBase.class, UserEntity.class, IUserEntityRepositoryFixture.class,
            ArticleEntity.class, IArticleEntityRepositoryFixture.class, ArticleType.class, Popularity.class}
)
public class ArticleValidatorTest {

    private IArticleEntityRepositoryFixture articleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultArticleValidator articleValidator;

    @BeforeEach
    void setup() {
        articleFixture = new IArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        articleValidator = new DefaultArticleValidator(articleFixture, userFixture);
    }

    @Test
    void canWrite_shouldReturnFalseIfUserRolesAreEmpty() {
        User user = new User(1L, "nick", Set.of());
        Article article = Article.of(null, null, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canWrite(article, user));
    }

    @Test
    void canWrite_shouldReturnFalseIfArticleHasId() {
        User user = new User(1L, "nick", Set.of(UserRole.USER));
        Article article = Article.of(1L, null, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canWrite(article, user));
    }

    @Test
    void canWrite_shouldReturnTrueForValidArticleAndUser() {
        User user = new User(1L, "nick", Set.of(UserRole.USER));
        Article article = Article.of(null, null, "nick", "Title", "Content", Popularity.empty(), false);

        assertTrue(articleValidator.canWrite(article, user));
    }

    @Test
    void canUpdate_shouldReturnFalseIfAuthorIdDoesNotMatchUserId() {
        User user = new User(2L, "nick", Set.of(UserRole.USER));
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canUpdate(article, user));
    }

    @Test
    void canUpdate_shouldReturnFalseIfArticleOrUserDoesNotExist() {
        User user = new User(1L, "nick", Set.of(UserRole.USER));
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canUpdate(article, user));
    }

    @Test
    void canUpdate_shouldReturnTrueIfArticleAndUserExistAndMatch() {
        User user = new User(1L, "nick", Set.of(UserRole.USER));
        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.NORMAL, "Title", "Content", null, false);
        UserEntity userEntity = UserEntity.of("encodedPass", "nick", Set.of(UserRole.USER));

        articleFixture.save(articleEntity);
        userFixture.save(userEntity);

        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertTrue(articleValidator.canUpdate(article, user));
    }

    @Test
    void canDelete_shouldReturnFalseIfAuthorIdDoesNotMatchUserId() {
        User user = new User(2L, "nick", Set.of(UserRole.USER));
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canDelete(article, user));
    }

    @Test
    void canDelete_shouldReturnFalseIfArticleOrUserDoesNotExist() {
        User user = new User(1L, "nick", Set.of(UserRole.USER));
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canDelete(article, user));
    }

    @Test
    void canDelete_shouldReturnTrueIfArticleAndUserExistAndMatch() {
        User user = new User(1L, "nick", Set.of(UserRole.USER));
        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.NORMAL, "Title", "Content", null, false);
        UserEntity userEntity = UserEntity.of("encodedPass", "nick", Set.of(UserRole.USER));

        articleFixture.save(articleEntity);
        userFixture.save(userEntity);

        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertTrue(articleValidator.canDelete(article, user));
    }
}