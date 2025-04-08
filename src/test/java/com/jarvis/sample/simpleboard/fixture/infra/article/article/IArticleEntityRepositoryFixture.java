package com.jarvis.sample.simpleboard.fixture.article.article;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;


import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_FIXTURE,
    references = { ArticleEntity.class, IArticleEntityRepository.class }
)
public class IArticleEntityRepositoryFixture implements IArticleEntityRepository {
    @Getter
    private final HashMap<Long, ArticleEntity> db = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<ArticleEntity> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public ArticleEntity save(ArticleEntity article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }

        if (article.getId() == null) {
            Long newId = idGenerator.getAndIncrement();
            FakeSetter.setField(article, "id", newId);
            db.put(newId, article);
        } else {
            db.put(article.getId(), article);
        }

        return article;
    }
}