package com.jarvis.sample.simpleboard.domain.article.api.announcement;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Announcement;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Announcement.class,  AnnouncementValidator.class }
 )
public class DefaultAnnouncementValidator implements AnnouncementValidator{
}
