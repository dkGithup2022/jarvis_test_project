package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Announcement.class, DefaultAnnouncementValidator.class, AnnouncementValidator.class }
)
public class AnnouncementValidatorTest {


}
