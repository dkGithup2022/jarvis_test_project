package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.domain.article.api.discussion.DefaultDiscussionWriter;
import com.jarvis.sample.simpleboard.domain.article.api.discussion.DiscussionWriter;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Discussion.class, DefaultDiscussionWriter.class, DiscussionWriter.class }
)
public class DefaultDiscussionWriterFixture implements DiscussionWriter {
    @Override
    public Discussion write(Discussion article) {
        return null;
    }

    @Override
    public Discussion update(Discussion article) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    // TODO: 필요 시 테스트용 목 동작 구현
}