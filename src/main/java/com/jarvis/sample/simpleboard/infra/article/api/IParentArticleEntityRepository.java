
package com.jarvis.sample.simpleboard.infra.article.api;


import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.Optional;

/**
 * IParentArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

@JarvisMeta(
        fileType = FileType.INFRA_REPOSITORY
)
/**
 * 도메인 계층에서 사용하는 추상 레포지토리입니다.
 */
public interface IParentArticleEntityRepository {
    // TODO: define methods if needed

    Optional<ParentArticleEntity> findById(Long id);

    ParentArticleEntity save(ParentArticleEntity entity);


}
