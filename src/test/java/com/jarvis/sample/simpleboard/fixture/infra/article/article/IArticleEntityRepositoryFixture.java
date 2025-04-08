
package com.jarvis.sample.simpleboard.fixture.article.article;

import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

/**
 * IArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { ArticleEntity.class, IArticleEntityRepository.class }
 )

public class IArticleEntityRepositoryFixture {
    // TODO: implement fixture methods if needed
}
