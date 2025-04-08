package com.jarvis.sample.simpleboard.infra.article.api;

import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@JarvisMeta(
        fileType = FileType.INFRA_REPOSITORY
)
/**
 * 도메인 계층에서 사용하는 추상 레포지토리입니다.
 */
public interface IChildArticleEntityRepository {

    Optional<ChildArticleEntity> findById(Long id);

    ChildArticleEntity save(ChildArticleEntity entity);

    List<ChildArticleEntity> listByParentIdOrderByOrder(Long parentId, Pageable pageable);
}
