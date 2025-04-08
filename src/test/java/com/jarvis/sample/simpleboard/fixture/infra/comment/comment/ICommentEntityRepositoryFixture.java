
package com.jarvis.sample.simpleboard.fixture.comment.comment;

import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

/**
 * ICommentEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { CommentEntity.class, ICommentEntityRepository.class }
 )

public class ICommentEntityRepositoryFixture {
    // TODO: implement fixture methods if needed
}
