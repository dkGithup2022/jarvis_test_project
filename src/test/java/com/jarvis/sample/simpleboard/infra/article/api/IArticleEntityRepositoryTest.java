package com.jarvis.sample.simpleboard.infra.article.api;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;

import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.jpa.ArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { ArticleEntity.class, IArticleEntityRepository.class, ArticleEntityRepository.class }
)
public class IArticleEntityRepositoryTest {

    @Autowired
    private ArticleEntityRepository repository;

    private ArticleEntity article;

    @BeforeEach
    void setUp() {
        PopularityEmbeddable popularity = new PopularityEmbeddable(100, 10, 1, 5);
        article = ArticleEntity.of(ArticleType.NORMAL, "Test Title", "Test Content", popularity, false);
        repository.save(article);
    }

    @Test
    void findById_shouldReturnArticleEntityWhenIdExists() {
        Optional<ArticleEntity> foundArticle = repository.findById(article.getId());
        assertTrue(foundArticle.isPresent());
        assertEquals(article.getId(), foundArticle.get().getId());
    }

    @Test
    void findById_shouldReturnEmptyWhenIdDoesNotExist() {
        Optional<ArticleEntity> foundArticle = repository.findById(999L);
        assertFalse(foundArticle.isPresent());
    }

    @Test
    void save_shouldPersistNewArticleEntity() {
        PopularityEmbeddable newPopularity = new PopularityEmbeddable(200, 20, 2, 10);
        ArticleEntity newArticle = ArticleEntity.of(ArticleType.ANNOUNCEMENT, "New Title", "New Content", newPopularity, false);
        
        ArticleEntity savedArticle = repository.save(newArticle);
        assertNotNull(savedArticle.getId());
        assertEquals("New Title", savedArticle.getTitle());
    }

    @Test
    void save_shouldUpdateExistingArticleEntity() {
        article = repository.findById(article.getId()).orElseThrow();
        article = ArticleEntity.of(article.getId(), ArticleType.QUESTION, "Updated Title", article.getContent(), article.getPopularityEmbeddable(), article.getDeleted());
        
        ArticleEntity updatedArticle = repository.save(article);
        assertEquals("Updated Title", updatedArticle.getTitle());
    }

    // Additional edge case test
    @Test
    void save_shouldNotPersistArticleWithNullTitle() {
        PopularityEmbeddable newPopularity = new PopularityEmbeddable(300, 30, 3, 15);
        ArticleEntity newArticle = ArticleEntity.of(ArticleType.DISCUSSION, null, "Content with no title", newPopularity, false);

        assertThrows(Exception.class, () -> repository.save(newArticle));
    }
}