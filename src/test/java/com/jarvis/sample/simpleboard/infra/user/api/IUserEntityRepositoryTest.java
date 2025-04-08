package com.jarvis.sample.simpleboard.infra.user.api;

import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.jpa.UserEntityRepository;



import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;


import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;


@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { UserEntity.class, IUserEntityRepository.class, UserEntityRepository.class }
)
public class IUserEntityRepositoryTest {

    @Test
    void test_defaultBehavior() {
        // TODO: implement tests
    }
}
