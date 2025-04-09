package com.jarvis.sample.simpleboard.domain.fixture.article.api.discussionReply;

import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DefaultDiscussionReplyWriter;
import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DiscussionReplyWriter;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { DiscussionReply.class, DefaultDiscussionReplyWriter.class, DiscussionReplyWriter.class }
)
public class DefaultDiscussionReplyWriterFixture implements DiscussionReplyWriter {
    @Override
    public DiscussionReply write(DiscussionReply article) {
        return null;
    }

    @Override
    public DiscussionReply update(DiscussionReply article) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    // TODO: 필요 시 테스트용 목 동작 구현
}