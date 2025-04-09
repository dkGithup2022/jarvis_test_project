package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DefaultDiscussionReplyReader;
import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DiscussionReplyReader;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { DiscussionReply.class, DefaultDiscussionReplyReader.class, DiscussionReplyReader.class }
)
public class DefaultDiscussionReplyReaderFixture implements DiscussionReplyReader {
    @Override
    public DiscussionReply read(Long articleId) {
        return null;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}