package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { Comment.class, CommentWriter.class }
)
public class DefaultCommentWriter implements CommentWriter {

    private final ICommentEntityRepository commentEntityRepository;

    @Override
    public Comment write(Comment comment) {
        if (comment.getId() != null) {
            throw new IllegalArgumentException("Comment ID must be null for write operation");
        }
        CommentEntity commentEntity = CommentEntity.of(
            null,
            comment.getArticleType(),
            comment.getArticleId(),
            comment.getContent(),
            comment.getParentId(),
            comment.getRootCommentOrder(),
            comment.getReplyOrder(),
            comment.getChildCount(),
            comment.isDeleted()
        );
        CommentEntity savedEntity = commentEntityRepository.save(commentEntity);
        return Comment.of(
            savedEntity.getId(),
            savedEntity.getArticleType(),
            savedEntity.getArticleId(),
            savedEntity.getContent(),
            savedEntity.getParentId(),
            savedEntity.getRootCommentOrder(),
            savedEntity.getReplyOrder(),
            savedEntity.getChildCount(),
            savedEntity.isDeleted()
        );
    }

    @Override
    public Comment update(Comment comment) {
        if (comment.getId() == null) {
            throw new IllegalArgumentException("Comment ID must not be null for update operation");
        }
        Optional<CommentEntity> existingEntityOpt = commentEntityRepository.findById(comment.getId());
        if (existingEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Comment with ID " + comment.getId() + " does not exist");
        }
        CommentEntity updatedEntity = CommentEntity.of(
            comment.getId(),
            comment.getArticleType(),
            comment.getArticleId(),
            comment.getContent(),
            comment.getParentId(),
            comment.getRootCommentOrder(),
            comment.getReplyOrder(),
            comment.getChildCount(),
            comment.isDeleted()
        );
        CommentEntity savedEntity = commentEntityRepository.save(updatedEntity);
        return Comment.of(
            savedEntity.getId(),
            savedEntity.getArticleType(),
            savedEntity.getArticleId(),
            savedEntity.getContent(),
            savedEntity.getParentId(),
            savedEntity.getRootCommentOrder(),
            savedEntity.getReplyOrder(),
            savedEntity.getChildCount(),
            savedEntity.isDeleted()
        );
    }
}