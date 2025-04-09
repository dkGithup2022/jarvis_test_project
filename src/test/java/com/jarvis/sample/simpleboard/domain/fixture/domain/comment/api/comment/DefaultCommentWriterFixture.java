package com.jarvis.sample.simpleboard.domain.fixture.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentWriter;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.DefaultCommentWriter;
import com.jarvis.sample.simpleboard.fixture.infra.comment.comment.ICommentEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Comment.class, DefaultCommentWriter.class, CommentWriter.class }
)
public class DefaultCommentWriterFixture implements CommentWriter {

    private final CommentWriter delegate;

    public DefaultCommentWriterFixture() {
        this.delegate = new DefaultCommentWriter(new ICommentEntityRepositoryFixture());
    }

    @Override
    public Comment write(Comment comment) {
        return delegate.write(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return delegate.update(comment);
    }
}

// The DefaultCommentWriterFixture class implements the CommentWriter interface.
// It uses an instance of DefaultCommentWriter, initialized with ICommentEntityRepositoryFixture, to delegate method calls.