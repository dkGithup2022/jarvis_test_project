package com.jarvis.sample.simpleboard.infra.article.jpa;


import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


/**
 * JPA 기반의 실제 레포지토리입니다.
 * Spring Data JPA가 자동 구현합니다.
 */
 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_IMPL,
     references = { ChildArticleEntity.class, IChildArticleEntityRepository.class }
 )
public interface ChildArticleEntityRepository extends JpaRepository<ChildArticleEntity, Long>, IChildArticleEntityRepository {
}
