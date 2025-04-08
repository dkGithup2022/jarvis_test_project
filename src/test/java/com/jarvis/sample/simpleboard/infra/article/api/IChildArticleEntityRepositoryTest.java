package com.jarvis.sample.simpleboard.infra.article.api;

import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.jpa.ChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@JarvisMeta(
        fileType = FileType.INFRA_REPOSITORY_TEST,
        references = {ChildArticleEntity.class, IChildArticleEntityRepository.class, ChildArticleEntityRepository.class}
)
public class IChildArticleEntityRepositoryTest {

    @Autowired
    private ChildArticleEntityRepository repository;

    private ChildArticleEntity childArticleEntity;

    private static Long authorId = 1L;

    @BeforeEach
    void setUp() {
        childArticleEntity = ChildArticleEntity.of(authorId, ArticleType.ARTICLE, "Title", "Content", new PopularityEmbeddable(10, 5, 1, 2), 1L, 1, false);
        repository.save(childArticleEntity);
    }

    @Test
    void findById_shouldReturnEntityWhenIdExists() {
        Optional<ChildArticleEntity> foundEntity = repository.findById(childArticleEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertEquals(childArticleEntity.getTitle(), foundEntity.get().getTitle());
    }

    @Test
    void findById_shouldReturnEmptyWhenIdDoesNotExist() {
        Optional<ChildArticleEntity> foundEntity = repository.findById(-1L);
        assertFalse(foundEntity.isPresent());
    }

    @Test
    void save_shouldPersistEntity() {
        ChildArticleEntity newEntity = ChildArticleEntity.of(authorId, ArticleType.ANNOUNCEMENT, "New Title", "New Content", new PopularityEmbeddable(0, 0, 0, 0), 1L, 2, false);
        ChildArticleEntity savedEntity = repository.save(newEntity);
        assertNotNull(savedEntity.getId());
        assertEquals("New Title", savedEntity.getTitle());
    }

    @Test
    void listByParentIdOrderByOrder_shouldReturnOrderedEntities() {
        ChildArticleEntity secondEntity = ChildArticleEntity.of(authorId, ArticleType.DISCUSSION, "Second Title", "Second Content", new PopularityEmbeddable(0, 0, 0, 0), 1L, 2, false);
        repository.save(secondEntity);

        Pageable pageable = PageRequest.of(0, 10);
        List<ChildArticleEntity> entities = repository.listByParentIdOrderByOrder(1L, pageable);

        assertEquals(2, entities.size());
        assertEquals(1, entities.get(0).getOrder());
        assertEquals(2, entities.get(1).getOrder());
    }

    @Test
    void listByParentIdOrderByOrder_shouldReturnEmptyListWhenNoEntitiesExist() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ChildArticleEntity> entities = repository.listByParentIdOrderByOrder(-1L, pageable);
        assertTrue(entities.isEmpty());
    }
}