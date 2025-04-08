package com.jarvis.sample.simpleboard.domain.comment.api.comment;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Comment.class, DefaultCommentWriter.class, CommentWriter.class }
)
public class CommentWriterTest {


}
