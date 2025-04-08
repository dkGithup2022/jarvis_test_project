package com.jarvis.sample.simpleboard.infra.article.api;

import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.article.jpa.ChildArticleEntityRepository;



import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;


@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { ChildArticleEntity.class, IChildArticleEntityRepository.class, ChildArticleEntityRepository.class }
)
public class IChildArticleEntityRepositoryTest {

    @Test
    void test_defaultBehavior() {
        // TODO: implement tests
    }
}
