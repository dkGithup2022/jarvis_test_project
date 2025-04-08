package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { DiscussionReply.class,  DiscussionReplyWriter.class }
 )
public class DefaultDiscussionReplyWriter implements DiscussionReplyWriter{
}
