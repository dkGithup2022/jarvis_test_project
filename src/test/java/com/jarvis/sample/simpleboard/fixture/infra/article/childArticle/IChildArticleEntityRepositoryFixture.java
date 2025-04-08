
package com.jarvis.sample.simpleboard.fixture.article.childArticle;

import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

/**
 * IChildArticleEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { ChildArticleEntity.class, IChildArticleEntityRepository.class }
 )

public class IChildArticleEntityRepositoryFixture {
    // TODO: implement fixture methods if needed
}
