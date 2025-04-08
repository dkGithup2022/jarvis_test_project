package com.jarvis.sample.simpleboard.infra.comment.api;

import com.jarvis.sample.simpleboard.infra.comment.CommentOrderEntity;
import com.jarvis.sample.simpleboard.infra.comment.api.ICommentOrderEntityRepository;
import com.jarvis.sample.simpleboard.infra.comment.jpa.CommentOrderEntityRepository;



import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;


@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { CommentOrderEntity.class, ICommentOrderEntityRepository.class, CommentOrderEntityRepository.class }
)
public class ICommentOrderEntityRepositoryTest {

    @Test
    void test_defaultBehavior() {
        // TODO: implement tests
    }
}
