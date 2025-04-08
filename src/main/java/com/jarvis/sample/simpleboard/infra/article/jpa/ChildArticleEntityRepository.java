package com.jarvis.sample.simpleboard.infra.article.jpa;

import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


/**
 * JPA 기반의 실제 레포지토리입니다.
 * Spring Data JPA가 자동 구현합니다.
 */
@JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_IMPL,
     references = { ChildArticleEntity.class }
)
public interface ChildArticleEntityRepository extends JpaRepository<ChildArticleEntity, Long>, IChildArticleEntityRepository {

    @Override
    Optional<ChildArticleEntity> findById(Long id);

    @Override
    ChildArticleEntity save(ChildArticleEntity entity);

    @Override
    @Query("SELECT c FROM ChildArticleEntity c WHERE c.parentId = ?1 ORDER BY c.order ASC")
    List<ChildArticleEntity> listByParentIdOrderByOrder(Long parentId, Pageable pageable);
}