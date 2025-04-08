
package com.jarvis.sample.simpleboard.infra.article.api;


import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * IArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

@JarvisMeta(
        fileType = FileType.INFRA_REPOSITORY
)
/**
 * 도메인 계층에서 사용하는 추상 레포지토리입니다.
 */
public interface IArticleEntityRepository {
    // TODO: define methods if needed

    Optional<ArticleEntity> findById(Long id);


    ArticleEntity save(ArticleEntity article);

}
