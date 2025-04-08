package com.jarvis.sample.simpleboard.infra.comment.api;

import com.jarvis.sample.simpleboard.infra.comment.CommentEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentEntityRepository;
import com.jarvis.sample.simpleboard.infra.comment.jpa.CommentEntityRepository;



import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;


@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { CommentEntity.class, ICommentEntityRepository.class, CommentEntityRepository.class }
)
public class ICommentEntityRepositoryTest {

    @Test
    void test_defaultBehavior() {
        // TODO: implement tests
    }
}
