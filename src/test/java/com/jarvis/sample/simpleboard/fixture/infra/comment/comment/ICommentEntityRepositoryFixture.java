package com.jarvis.sample.simpleboard.fixture.comment.comment;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * ICommentEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_FIXTURE,
    references = { CommentEntity.class, ICommentEntityRepository.class }
)
public class ICommentEntityRepositoryFixture implements ICommentEntityRepository {
    private final HashMap<Long, CommentEntity> db = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Optional<CommentEntity> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public CommentEntity save(CommentEntity entity) {
        if (entity != null && entity.getId() != null) {
            db.put(entity.getId(), entity);
            return entity;
        }
        Long newId = idGenerator.incrementAndGet();
        FakeSetter.setField(entity, "id", newId);
        db.put(newId, entity);
        return entity;
    }

    @Override
    public List<CommentEntity> listByArticleId(ArticleType articleType, Long articleId, PageRequest pageRequest) {
        return db.values().stream()
            .filter(comment -> comment.getArticleType().equals(articleType) && comment.getArticleId().equals(articleId))
            .sorted((c1, c2) -> Long.compare(c1.getId(), c2.getId()))
            .skip(pageRequest.getPageNumber() * pageRequest.getPageSize())
            .limit(pageRequest.getPageSize())
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Integer> findMaxRootOrderByArticleId(Long articleId) {
        return db.values().stream()
            .filter(comment -> comment.getArticleId().equals(articleId))
            .map(CommentEntity::getRootCommentOrder)
            .max(Integer::compareTo);
    }

    @Override
    public Optional<CommentEntity> findTopByParentIdOrderByCommentSeqDesc(Long parentId) {
        return db.values().stream()
            .filter(comment -> comment.getParentId() != null && comment.getParentId().equals(parentId))
            .max((c1, c2) -> Integer.compare(c1.getReplyOrder(), c2.getReplyOrder()));
    }
}

// FakeSetter is assumed to be a utility class to set private fields for testing purposes.