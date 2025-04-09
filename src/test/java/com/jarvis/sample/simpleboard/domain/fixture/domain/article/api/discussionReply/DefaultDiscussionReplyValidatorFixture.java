package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DefaultDiscussionReplyValidator;
import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DiscussionReplyValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { DiscussionReply.class, DefaultDiscussionReplyValidator.class, DiscussionReplyValidator.class }
)
public class DefaultDiscussionReplyValidatorFixture implements DiscussionReplyValidator {
    @Override
    public boolean canWrite(DiscussionReply article, User user) {
        return false;
    }

    @Override
    public boolean canUpdate(DiscussionReply article, User user) {
        return false;
    }

    @Override
    public boolean canDelete(DiscussionReply article, User user) {
        return false;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}