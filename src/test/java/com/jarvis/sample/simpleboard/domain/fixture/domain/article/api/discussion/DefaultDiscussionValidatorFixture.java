package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.domain.article.api.discussion.DefaultDiscussionValidator;
import com.jarvis.sample.simpleboard.domain.article.api.discussion.DiscussionValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Discussion.class, DefaultDiscussionValidator.class, DiscussionValidator.class }
)
public class DefaultDiscussionValidatorFixture implements DiscussionValidator {
    @Override
    public boolean canWrite(Discussion article, User user) {
        return false;
    }

    @Override
    public boolean canUpdate(Discussion article, User user) {
        return false;
    }

    @Override
    public boolean canDelete(Discussion article, User user) {
        return false;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}