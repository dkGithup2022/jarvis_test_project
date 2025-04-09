package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
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

    private UserEntity saveUser(IUserEntityRepositoryFixture fixture, Long id) {
        UserEntity user = UserEntity.of("passwordEncoded", "nickname", Set.of(UserRole.USER));
        FakeSetter.setField(user, "id", 1L);
        return fixture.save(user);
    }

    private User getUserDomain(Long id) {
        var entity = userFixture.findById(id).get();
        return User.of(entity.getId(), entity.getNickname(), entity.getUserRole());
    }

    @Test
    void canWrite_shouldReturnFalseIfUserRolesAreEmpty() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(null, null, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canWrite(article, getUserDomain(1L)));
    }

    @Test
    void canWrite_shouldReturnFalseIfArticleHasId() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(1L, null, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canWrite(article, getUserDomain(1L)));
    }

    @Test
    void canWrite_shouldReturnTrueForValidArticleAndUser() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(null, null, "nick", "Title", "Content", Popularity.empty(), false);

        assertTrue(articleValidator.canWrite(article, getUserDomain(1L)));
    }

    @Test
    void canUpdate_shouldReturnFalseIfAuthorIdDoesNotMatchUserId() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canUpdate(article, getUserDomain(1L)));
    }

    @Test
    void canUpdate_shouldReturnFalseIfArticleOrUserDoesNotExist() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canUpdate(article, getUserDomain(1L)));
    }

    @Test
    void canUpdate_shouldReturnTrueIfArticleAndUserExistAndMatch() {
        var user = saveUser(userFixture, 1L);
        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.ARTICLE, "Title", "Content", null, false);

        articleFixture.save(articleEntity);

        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertTrue(articleValidator.canUpdate(article, getUserDomain(1L)));
    }

    @Test
    void canDelete_shouldReturnFalseIfAuthorIdDoesNotMatchUserId() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canDelete(article,  getUserDomain(1L)));
    }

    @Test
    void canDelete_shouldReturnFalseIfArticleOrUserDoesNotExist() {
        var user = saveUser(userFixture, 1L);
        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertFalse(articleValidator.canDelete(article, getUserDomain(1L)));
    }

    @Test
    void canDelete_shouldReturnTrueIfArticleAndUserExistAndMatch() {
        var user = saveUser(userFixture, 1L);
        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.ARTICLE, "Title", "Content", null, false);
        UserEntity userEntity = UserEntity.of("encodedPass", "nick", Set.of(UserRole.USER));

        articleFixture.save(articleEntity);
        userFixture.save(userEntity);

        Article article = Article.of(1L, 1L, "nick", "Title", "Content", Popularity.empty(), false);

        assertTrue(articleValidator.canDelete(article, getUserDomain(1L)));
    }
}