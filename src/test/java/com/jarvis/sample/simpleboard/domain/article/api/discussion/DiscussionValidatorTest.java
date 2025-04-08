package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Discussion.class, DefaultDiscussionValidator.class, DiscussionValidator.class }
)
public class DiscussionValidatorTest {


}
