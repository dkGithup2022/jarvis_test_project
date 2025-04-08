package com.jarvis.sample.simpleboard.infra.article.api;

import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.jpa.ParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

@DataJpaTest
public class IParentArticleEntityRepositoryTest {

    @Autowired
    private ParentArticleEntityRepository repository;

    private ParentArticleEntity testEntity;

    @BeforeEach
    void setUp() {
        PopularityEmbeddable popularity = new PopularityEmbeddable(100, 10, 1, 5);
        testEntity = ParentArticleEntity.of(ArticleType.ARTICLE, "Test Title", "Test Content", 1L, popularity, false);
        repository.save(testEntity);
    }

    @Test
    void findById_shouldReturnEntityWhenIdExists() {
        Optional<ParentArticleEntity> foundEntity = repository.findById(testEntity.getId());
        Assertions.assertTrue(foundEntity.isPresent());
        Assertions.assertEquals(testEntity.getTitle(), foundEntity.get().getTitle());
    }

    @Test
    void findById_shouldReturnEmptyWhenIdDoesNotExist() {
        Optional<ParentArticleEntity> foundEntity = repository.findById(-1L);
        Assertions.assertFalse(foundEntity.isPresent());
    }

    @Test
    void save_shouldPersistEntity() {
        PopularityEmbeddable newPopularity = new PopularityEmbeddable(200, 20, 2, 10);
        ParentArticleEntity newEntity = ParentArticleEntity.of(ArticleType.ANNOUNCEMENT, "New Title", "New Content", 2L, newPopularity, false);

        ParentArticleEntity savedEntity = repository.save(newEntity);
        Assertions.assertNotNull(savedEntity.getId());
        Assertions.assertEquals("New Title", savedEntity.getTitle());
    }

    @Test
    void save_shouldUpdateExistingEntity() {
        testEntity = ParentArticleEntity.of(testEntity.getId(), ArticleType.DISCUSSION, "Updated Title", "Updated Content", 1L, testEntity.getPopularityEmbeddable(), true);
        ParentArticleEntity updatedEntity = repository.save(testEntity);

        Assertions.assertEquals("Updated Title", updatedEntity.getTitle());
        Assertions.assertTrue(updatedEntity.getDeleted());
    }

    // Additional test cases to ensure robustness
    @Test
    void save_shouldNotPersistEntityWithNullTitle() {
        PopularityEmbeddable popularity = new PopularityEmbeddable(50, 5, 0, 2);
        ParentArticleEntity invalidEntity = ParentArticleEntity.of(ArticleType.ARTICLE, null, "Content without title", 3L, popularity, false);

        Assertions.assertThrows(Exception.class, () -> repository.save(invalidEntity));
    }

    @Test
    void save_shouldNotPersistEntityWithNullContent() {
        PopularityEmbeddable popularity = new PopularityEmbeddable(50, 5, 0, 2);
        ParentArticleEntity invalidEntity = ParentArticleEntity.of(ArticleType.ARTICLE, "Title without content", null, 3L, popularity, false);

        Assertions.assertThrows(Exception.class, () -> repository.save(invalidEntity));
    }
}