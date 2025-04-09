package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Article;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.article.IArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Article.class, DefaultArticleWriter.class, ArticleWriter.class,
                ArticleWriterBase.class,
                UserEntity.class, IUserEntityRepositoryFixture.class,
                ArticleEntity.class, IArticleEntityRepositoryFixture.class,
                User.class, PopularityEmbeddable.class,
                UserRole.class,
                ArticleType.class, Popularity.class
        }
)
public class ArticleWriterTest {

    private IArticleEntityRepositoryFixture articleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultArticleWriter articleWriter;

    @BeforeEach
    void setup() {
        articleFixture = new IArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        articleWriter = new DefaultArticleWriter(articleFixture, userFixture);
    }

    @Test
    void write_shouldSaveNewArticle() {
        UserEntity author = UserEntity.of("encodedPassword", "authorNickname", Set.of());
        userFixture.save(author);

        Article article = Article.of(null, 1L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        Article savedArticle = articleWriter.write(article);

        assertNotNull(savedArticle.getId());
        assertEquals(article.getTitle(), savedArticle.getTitle());
        assertEquals(article.getContent(), savedArticle.getContent());
        assertEquals(article.getAuthorId(), savedArticle.getAuthorId());
        assertFalse(savedArticle.getDeleted());
    }

    @Test
    void write_shouldThrowExceptionIfIdIsPresent() {
        Article article = Article.of(1L, 1L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        Exception exception = assertThrows(RuntimeException.class, () -> articleWriter.write(article));
        assertEquals("Article ID must not be present when writing a new article.", exception.getMessage());
    }

    @Test
    void write_shouldThrowExceptionIfAuthorNotFound() {
        Article article = Article.of(null, 99L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        Exception exception = assertThrows(RuntimeException.class, () -> articleWriter.write(article));
        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void update_shouldUpdateExistingArticle() {
        UserEntity author = UserEntity.of("encodedPassword", "authorNickname", Set.of());
        userFixture.save(author);

        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.ARTICLE, "Title", "Content", null, false);
        articleFixture.save(articleEntity);

        Article article = Article.of(1L, 1L, "authorNickname", "Updated Title", "Updated Content", Popularity.empty(), false);

        Article updatedArticle = articleWriter.update(article);

        assertEquals(article.getId(), updatedArticle.getId());
        assertEquals("Updated Title", updatedArticle.getTitle());
        assertEquals("Updated Content", updatedArticle.getContent());
    }

    @Test
    void update_shouldThrowExceptionIfIdIsNull() {
        Article article = Article.of(null, 1L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        Exception exception = assertThrows(RuntimeException.class, () -> articleWriter.update(article));
        assertEquals("Article ID must be present when updating an article.", exception.getMessage());
    }

    @Test
    void update_shouldThrowExceptionIfArticleNotFound() {
        Article article = Article.of(99L, 1L, "authorNickname", "Title", "Content", Popularity.empty(), false);

        Exception exception = assertThrows(RuntimeException.class, () -> articleWriter.update(article));
        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void delete_shouldMarkArticleAsDeleted() {
        ArticleEntity articleEntity = ArticleEntity.of(1L, 1L, ArticleType.ARTICLE, "Title", "Content", null, false);
        articleFixture.save(articleEntity);

        articleWriter.delete(1L);

        Optional<ArticleEntity> deletedArticle = articleFixture.findById(1L);
        assertTrue(deletedArticle.isPresent());
        assertTrue(deletedArticle.get().getDeleted());
    }

    @Test
    void delete_shouldThrowExceptionIfArticleNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> articleWriter.delete(99L));
        assertEquals("Article not found", exception.getMessage());
    }
}