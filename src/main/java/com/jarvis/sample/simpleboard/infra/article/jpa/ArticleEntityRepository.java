package com.jarvis.sample.simpleboard.infra.article.jpa;

import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA 기반의 실제 레포지토리입니다.
 * Spring Data JPA가 자동 구현합니다.
 */
public interface ArticleEntityRepository extends JpaRepository<ArticleEntity, Long>, IArticleEntityRepository {

    @Override
    Optional<ArticleEntity> findById(Long id);

    @Override
    ArticleEntity save(ArticleEntity article);
}