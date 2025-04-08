package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Discussion.class,  DiscussionWriter.class }
 )
public class DefaultDiscussionWriter implements DiscussionWriter{
}
