package com.jarvis.sample.simpleboard.infra.article.api;

import com.jarvis.sample.simpleboard.infra.article.ArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.article.jpa.ArticleEntityRepository;



import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;


@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { ArticleEntity.class, IArticleEntityRepository.class, ArticleEntityRepository.class }
)
public class IArticleEntityRepositoryTest {

    @Test
    void test_defaultBehavior() {
        // TODO: implement tests
    }
}
