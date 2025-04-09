package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.domain.article.api.discussion.DefaultDiscussionReader;
import com.jarvis.sample.simpleboard.domain.article.api.discussion.DiscussionReader;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Discussion.class, DefaultDiscussionReader.class, DiscussionReader.class }
)
public class DefaultDiscussionReaderFixture implements DiscussionReader {
    @Override
    public Discussion read(Long articleId) {
        return null;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}