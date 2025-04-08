package com.jarvis.sample.simpleboard.domain.user.api.user;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.user.specs.User;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { User.class, DefaultUserReader.class, UserReader.class }
)
public class UserReaderTest {


}
