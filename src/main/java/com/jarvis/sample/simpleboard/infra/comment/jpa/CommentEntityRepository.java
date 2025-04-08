package com.jarvis.sample.simpleboard.infra.comment.jpa;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * JPA 기반의 실제 레포지토리입니다.
 * Spring Data JPA가 자동 구현합니다.
 */
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_IMPL,
    references = { CommentEntity.class, ICommentEntityRepository.class }
)
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long>, ICommentEntityRepository {

    @Override
    Optional<CommentEntity> findById(Long id);

    @Override
    CommentEntity save(CommentEntity entity);

    @Override
    @Query("SELECT MAX(c.rootCommentOrder) FROM CommentEntity c WHERE c.articleId = ?1")
    Optional<Integer> findMaxRootOrderByArticleId(Long articleId);

    @Override
    Optional<CommentEntity> findTopByParentIdOrderByCommentSeqDesc(Long parentId);

    @Override
    @Query("SELECT c FROM CommentEntity c WHERE c.articleType = ?1 AND c.articleId = ?2")
    List<CommentEntity> listByArticleId(ArticleType articleType, Long articleId, PageRequest pageRequest);
}