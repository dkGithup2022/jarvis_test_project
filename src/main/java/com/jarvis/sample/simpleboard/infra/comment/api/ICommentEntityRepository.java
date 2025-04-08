
package com.jarvis.sample.simpleboard.infra.comment.api;


import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.Optional;

/**
 * ICommentEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY
 )
/**
 * 도메인 계층에서 사용하는 추상 레포지토리입니다.
 */
public interface ICommentEntityRepository {
    // TODO: define methods if needed

 Optional<CommentEntity> findById(Long id);             // 1. 단건 조회

 CommentEntity save(CommentEntity entity);              // 2. 삽입 및 수정


 Optional<Integer> findMaxRootOrderByArticleId(Long articleId);

 Optional<CommentEntity> findTopByParentIdOrderByCommentSeqDesc( Long parentId); // 3. 대댓글 순번

}
