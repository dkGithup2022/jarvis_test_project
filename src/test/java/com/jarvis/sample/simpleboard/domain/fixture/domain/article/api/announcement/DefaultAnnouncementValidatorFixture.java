package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.domain.article.api.announcement.AnnouncementValidator;
import com.jarvis.sample.simpleboard.domain.article.api.announcement.DefaultAnnouncementValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Announcement.class, DefaultAnnouncementValidator.class, AnnouncementValidator.class }
)
public class DefaultAnnouncementValidatorFixture implements AnnouncementValidator {
    @Override
    public boolean canWrite(Announcement article, User user) {
        return false;
    }

    @Override
    public boolean canUpdate(Announcement article, User user) {
        return false;
    }

    @Override
    public boolean canDelete(Announcement article, User user) {
        return false;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}