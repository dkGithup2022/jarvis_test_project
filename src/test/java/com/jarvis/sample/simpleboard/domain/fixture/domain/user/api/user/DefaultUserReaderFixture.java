package com.jarvis.sample.simpleboard.domain.fixture.user.api.user;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.user.specs.User;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { User.class, DefaultUserReader.class, UserReader.class }
)
public class DefaultUserReaderFixture implements UserReader {
    // TODO: 필요 시 테스트용 목 동작 구현
}