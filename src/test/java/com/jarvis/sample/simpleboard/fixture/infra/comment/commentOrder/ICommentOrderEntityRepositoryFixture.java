
package com.jarvis.sample.simpleboard.fixture.comment.commentOrder;

import com.jarvis.sample.simpleboard.infra.comment.CommentOrderEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentOrderEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

/**
 * ICommentOrderEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { CommentOrderEntity.class, ICommentOrderEntityRepository.class }
 )

public class ICommentOrderEntityRepositoryFixture {
    // TODO: implement fixture methods if needed
}
