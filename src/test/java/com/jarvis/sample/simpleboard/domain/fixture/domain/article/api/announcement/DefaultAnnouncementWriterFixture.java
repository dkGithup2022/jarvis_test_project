package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.domain.article.api.announcement.AnnouncementWriter;
import com.jarvis.sample.simpleboard.domain.article.api.announcement.DefaultAnnouncementWriter;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Announcement.class, DefaultAnnouncementWriter.class, AnnouncementWriter.class }
)
public class DefaultAnnouncementWriterFixture implements AnnouncementWriter {
    @Override
    public Announcement write(Announcement article) {
        return null;
    }

    @Override
    public Announcement update(Announcement article) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    // TODO: 필요 시 테스트용 목 동작 구현
}