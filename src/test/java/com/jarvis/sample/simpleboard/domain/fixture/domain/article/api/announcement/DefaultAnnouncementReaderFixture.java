package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.domain.article.api.announcement.AnnouncementReader;
import com.jarvis.sample.simpleboard.domain.article.api.announcement.DefaultAnnouncementReader;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Announcement.class, DefaultAnnouncementReader.class, AnnouncementReader.class }
)
public class DefaultAnnouncementReaderFixture implements AnnouncementReader {
    @Override
    public Announcement read(Long articleId) {
        return null;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}