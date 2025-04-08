package com.jarvis.sample.simpleboard.domain.fixture.comment.api.comment;

import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentWriter;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.DefaultCommentWriter;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Comment.class, DefaultCommentWriter.class, CommentWriter.class }
)
public class DefaultCommentWriterFixture implements CommentWriter {
    // TODO: 필요 시 테스트용 목 동작 구현
}