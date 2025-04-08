package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.comment.specs.ArticleType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.DefaultCommentWriter;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentWriter;
import com.jarvis.sample.simpleboard.infra.comment.ICommentEntityRepositoryFixture;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Comment.class, DefaultCommentWriter.class, CommentWriter.class }
)
public class CommentWriterTest {

    private ICommentEntityRepositoryFixture fixture;
    private DefaultCommentWriter commentWriter;

    @BeforeEach
    void setup() {
        fixture = new ICommentEntityRepositoryFixture();
        commentWriter = new DefaultCommentWriter(fixture);
    }

    @Test
    void write_shouldPersistNewComment() {
        Comment comment = Comment.of(
            null,
            ArticleType.NORMAL,
            1L,
            "This is a test comment",
            null,
            1,
            1,
            0,
            false
        );

        Comment result = commentWriter.write(comment);

        assertNotNull(result.getId());
        assertEquals(comment.getArticleId(), result.getArticleId());
        assertEquals(comment.getContent(), result.getContent());
    }

    @Test
    void write_shouldThrowException_whenCommentIdIsNotNull() {
        Comment comment = Comment.of(
            1L,
            ArticleType.NORMAL,
            1L,
            "This is a test comment",
            null,
            1,
            1,
            0,
            false
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentWriter.write(comment);
        });

        assertEquals("Comment ID must be null for write operation", exception.getMessage());
    }

    @Test
    void update_shouldUpdateExistingComment() {
        Comment comment = Comment.of(
            null,
            ArticleType.NORMAL,
            1L,
            "This is a test comment",
            null,
            1,
            1,
            0,
            false
        );

        Comment savedComment = commentWriter.write(comment);

        Comment updatedComment = Comment.of(
            savedComment.getId(),
            ArticleType.NORMAL,
            1L,
            "This comment has been updated",
            null,
            1,
            1,
            0,
            false
        );

        Comment result = commentWriter.update(updatedComment);

        assertEquals(savedComment.getId(), result.getId());
        assertEquals("This comment has been updated", result.getContent());
    }

    @Test
    void update_shouldThrowException_whenCommentIdIsNull() {
        Comment comment = Comment.of(
            null,
            ArticleType.NORMAL,
            1L,
            "This is a test comment",
            null,
            1,
            1,
            0,
            false
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentWriter.update(comment);
        });

        assertEquals("Comment ID must not be null for update operation", exception.getMessage());
    }

    @Test
    void update_shouldThrowException_whenCommentDoesNotExist() {
        Comment comment = Comment.of(
            999L,
            ArticleType.NORMAL,
            1L,
            "This is a non-existing comment",
            null,
            1,
            1,
            0,
            false
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentWriter.update(comment);
        });

        assertEquals("Comment with ID 999 does not exist", exception.getMessage());
    }
}