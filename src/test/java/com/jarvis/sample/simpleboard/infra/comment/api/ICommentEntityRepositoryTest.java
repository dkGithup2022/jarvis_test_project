package com.jarvis.sample.simpleboard.infra.comment.api;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.jpa.CommentEntityRepository;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
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
    references = { CommentEntity.class, ICommentEntityRepository.class, CommentEntityRepository.class }
)
public class ICommentEntityRepositoryTest {

    @Autowired
    private CommentEntityRepository repository;

    private CommentEntity commentEntity;

    @BeforeEach
    void setUp() {
        commentEntity =CommentEntity.of(null, ArticleType.NORMAL, 1L, "Sample Comment", null, 1, null, 0, false);
        repository.save(commentEntity);
    }

    @Test
    void findById_shouldReturnCommentEntityWhenExists() {
        Optional<CommentEntity> foundEntity = repository.findById(commentEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertEquals(commentEntity.getContent(), foundEntity.get().getContent());
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        Optional<CommentEntity> foundEntity = repository.findById(999L);
        assertFalse(foundEntity.isPresent());
    }

    @Test
    void save_shouldAssignIdWhenSavingNewEntity() {
        CommentEntity newComment =  CommentEntity.of(null, ArticleType.ANNOUNCEMENT, 2L, "New Comment", null, 2, null, 0, false);
        CommentEntity savedEntity = repository.save(newComment);
        assertNotNull(savedEntity.getId());
    }

    @Test
    void save_shouldUpdateExistingEntity() {
        FakeSetter.setField(commentEntity,"content", "Updated Content");
        CommentEntity updatedEntity = repository.save(commentEntity);
        assertEquals("Updated Content", updatedEntity.getContent());
    }

    @Test
    void findMaxRootOrderByArticleId_shouldReturnMaxRootOrder() {
        Optional<Integer> maxRootOrder = repository.findMaxRootOrderByArticleId(1L);
        assertTrue(maxRootOrder.isPresent());
        assertEquals(1, maxRootOrder.get());
    }

    @Test
    void findMaxRootOrderByArticleId_shouldReturnEmptyWhenNoComments() {
        Optional<Integer> maxRootOrder = repository.findMaxRootOrderByArticleId(999L);
        assertFalse(maxRootOrder.isPresent());
    }

    @Test
    void findTopByParentIdOrderByCommentSeqDesc_shouldReturnTopCommentWhenExists() {
        CommentEntity replyComment = CommentEntity.of(null, ArticleType.NORMAL, 1L, "Reply Comment", commentEntity.getId(), 1, 1, 0, false);
        repository.save(replyComment);

        Optional<CommentEntity> topReply = repository.findTopByParentIdOrderByCommentSeqDesc(commentEntity.getId());
        assertTrue(topReply.isPresent());
        assertEquals("Reply Comment", topReply.get().getContent());
    }

    @Test
    void findTopByParentIdOrderByCommentSeqDesc_shouldReturnEmptyWhenNoReplies() {
        Optional<CommentEntity> topReply = repository.findTopByParentIdOrderByCommentSeqDesc(999L);
        assertFalse(topReply.isPresent());
    }
}