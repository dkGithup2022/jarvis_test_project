package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { DiscussionReply.class,  DiscussionReplyValidator.class }
 )
public class DefaultDiscussionReplyValidator implements DiscussionReplyValidator{
}
