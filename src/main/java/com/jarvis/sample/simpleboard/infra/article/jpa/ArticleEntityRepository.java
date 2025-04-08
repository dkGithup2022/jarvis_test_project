package com.jarvis.sample.simpleboard.infra.article.jpa;

import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
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

    /**
     * 특정 키워드가 포함된 게시글을 최신순으로 조회합니다 (native query 사용).
     */
    @Query(
            value = "SELECT * FROM article_entity " +
                    "WHERE MATCH(title, content) AGAINST (?1 IN BOOLEAN MODE) " +
                    "ORDER BY id DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<ArticleEntity> searchByKeywordWithPagination(String keyword, int limit, int offset);
}