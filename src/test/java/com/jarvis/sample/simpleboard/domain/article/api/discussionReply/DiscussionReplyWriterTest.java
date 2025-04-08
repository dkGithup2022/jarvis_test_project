package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { DiscussionReply.class, DefaultDiscussionReplyWriter.class, DiscussionReplyWriter.class }
)
public class DiscussionReplyWriterTest {


}
