package com.jarvis.sample.simpleboard.fixture.infra.article.childArticle;

import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * IChildArticleEntityRepository 테스트용 픽스처 클래스.
 */
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_FIXTURE,
    references = { ChildArticleEntity.class, IChildArticleEntityRepository.class }
)
public class IChildArticleEntityRepositoryFixture implements IChildArticleEntityRepository {

    @Getter
    private final Map<Long, ChildArticleEntity> db = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Optional<ChildArticleEntity> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public ChildArticleEntity save(ChildArticleEntity entity) {
        if (entity != null && entity.getId() != null) {
            db.put(entity.getId(), entity);
            return entity;
        }
        long newId = idGenerator.incrementAndGet();
        ChildArticleEntity newEntity = ChildArticleEntity.of(
            newId,
            entity.getAuthorId(),
            entity.getArticleType(),
            entity.getTitle(),
            entity.getContent(),
            entity.getPopularityEmbeddable(),
            entity.getParentId(),
            entity.getOrder(),
            entity.getDeleted()
        );
        db.put(newId, newEntity);
        return newEntity;
    }

    @Override
    public List<ChildArticleEntity> listByParentIdOrderByOrder(Long parentId, Pageable pageable) {
        return db.values().stream()
            .filter(entity -> Objects.equals(entity.getParentId(), parentId))
            .sorted(Comparator.comparingInt(ChildArticleEntity::getOrder))
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(Collectors.toList());
    }
}