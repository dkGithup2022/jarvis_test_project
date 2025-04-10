package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.fixture.infra.comment.comment.ICommentEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Comment.class,
                DefaultCommentReader.class,
                CommentReader.class,
                ICommentEntityRepository.class,
                ICommentEntityRepositoryFixture.class,
                CommentEntity.class,
                ArticleType.class
        }
)
public class CommentReaderTest {

    private ICommentEntityRepositoryFixture fixture;
    private DefaultCommentReader commentReader;

    @BeforeEach
    void setup() {
        fixture = new ICommentEntityRepositoryFixture();
        commentReader = new DefaultCommentReader(fixture);
    }

    @Test
    void listByArticleInfo_shouldReturnCommentsForGivenArticle() {
        ArticleType articleType = ArticleType.ANSWER;
        Long articleId = 1L;
        CommentEntity c1 = CommentEntity.of(1L, articleType, articleId, "Comment 1", null, 1, 0, 0, false);
        CommentEntity c2 = CommentEntity.of(2L, articleType, articleId, "Comment 2", null, 2, 0, 0, false);
        CommentEntity c3 = CommentEntity.of(3L, articleType, 2L, "Comment 3", null, 1, 0, 0, false); // Different articleId

        fixture.save(c1);
        fixture.save(c2);
        fixture.save(c3);

        List<Comment> result = commentReader.listByArticleInfo(articleType, articleId, 0, 10);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getArticleId().equals(articleId)));
    }

    @Test
    void findById_shouldReturnCommentWhenIdExists() {
        CommentEntity c1 = CommentEntity.of(1L, ArticleType.ANSWER, 1L, "Comment 1", null, 1, 0, 0, false);
        fixture.save(c1);

        Comment result = commentReader.findById(1L);

        assertNotNull(result);
        assertEquals(c1.getId(), result.getId());
    }

    @Test
    void findById_shouldReturnNullWhenIdDoesNotExist() {
        Comment result = commentReader.findById(999L);

        assertNull(result);
    }

    @Test
    void listByArticleInfo_shouldReturnEmptyListWhenNoCommentsForArticle() {
        ArticleType articleType = ArticleType.ANSWER;
        Long articleId = 1L;

        List<Comment> result = commentReader.listByArticleInfo(articleType, articleId, 0, 10);

        assertTrue(result.isEmpty());
    }
}