package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {Comment.class, CommentReader.class,
                ArticleReaderBase.class,
                UserEntity.class,
                IUserEntityRepository.class,
                ParentArticleEntity.class,
                IParentArticleEntityRepository.class,
                ArticleType.class,
                UserRole.class
        }
)
public class DefaultCommentReader implements CommentReader {

    private final ICommentEntityRepository commentEntityRepository;

    @Override
    public List<Comment> listByArticleInfo(ArticleType articleType, Long articleId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        List<CommentEntity> commentEntities = commentEntityRepository.listByArticleId(articleType, articleId, pageRequest);
        return commentEntities.stream()
                .map(this::convertToDomain)
                .collect(Collectors.toList());
    }

    private Comment convertToDomain(CommentEntity entity) {
        return Comment.of(
                entity.getId(),
                entity.getArticleType(),
                entity.getArticleId(),
                entity.getContent(),
                entity.getParentId(),
                entity.getRootCommentOrder(),
                entity.getReplyOrder(),
                entity.getChildCount(),
                entity.isDeleted()
        );
    }
}

/*
Inline Comments:

1. The `DefaultCommentReader` class implements the `CommentReader` interface and provides the implementation for the `listByArticleInfo` method.
2. The `listByArticleInfo` method uses `PageRequest` to handle pagination and retrieves a list of `CommentEntity` objects from the repository.
3. The `convertToDomain` method maps a `CommentEntity` object to a `Comment` domain object.
4. The `ICommentEntityRepository` is assumed to have a method `findByArticleTypeAndArticleId` for fetching comments by article type and ID with pagination.
*/