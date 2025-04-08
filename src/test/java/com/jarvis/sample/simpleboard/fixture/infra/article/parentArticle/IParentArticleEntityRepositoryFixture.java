
package com.jarvis.sample.simpleboard.fixture.article.parentArticle;

import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

/**
 * IParentArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { ParentArticleEntity.class, IParentArticleEntityRepository.class }
 )

public class IParentArticleEntityRepositoryFixture {
    // TODO: implement fixture methods if needed
}
