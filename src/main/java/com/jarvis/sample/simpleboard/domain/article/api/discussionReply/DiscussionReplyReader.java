package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;


import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface DiscussionReplyReader extends ArticleReaderBase<DiscussionReply> {
}
