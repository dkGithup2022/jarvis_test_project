package com.jarvis.sample.simpleboard.fixture.article.parentArticle;

import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IParentArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_FIXTURE,
    references = { ParentArticleEntity.class, IParentArticleEntityRepository.class }
)
public class IParentArticleEntityRepositoryFixture implements IParentArticleEntityRepository {
    @Getter
    private HashMap<Long, ParentArticleEntity> db = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong();

    @Override
    public Optional<ParentArticleEntity> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public ParentArticleEntity save(ParentArticleEntity entity) {
        if (entity != null && entity.getId() != null) {
            db.put(entity.getId(), entity);
            return entity;
        }
        Long newId = idGenerator.incrementAndGet();
        FakeSetter.setField(entity, "id", newId);
        db.put(newId, entity);
        return entity;
    }
}

// Note: FakeSetter is assumed to be a utility class that allows setting private fields for testing purposes.