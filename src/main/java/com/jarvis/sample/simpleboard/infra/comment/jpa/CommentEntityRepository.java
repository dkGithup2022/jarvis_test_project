package com.jarvis.sample.simpleboard.infra.comment.jpa;


import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


/**
 * JPA 기반의 실제 레포지토리입니다.
 * Spring Data JPA가 자동 구현합니다.
 */
 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_IMPL,
     references = { CommentEntity.class, ICommentEntityRepository.class }
 )
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long>, ICommentEntityRepository {
}
