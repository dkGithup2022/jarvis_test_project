package com.jarvis.sample.simpleboard.domain.fixture.domain.comment.api.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentReader;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.DefaultCommentReader;
import com.jarvis.sample.simpleboard.fixture.infra.comment.comment.ICommentEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;

import java.util.List;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_FIXTURE,
        references = {Comment.class, DefaultCommentReader.class, CommentReader.class}
)
public class DefaultCommentReaderFixture implements CommentReader {

    CommentReader delegate;

    public DefaultCommentReaderFixture() {
        delegate = new DefaultCommentReader(new ICommentEntityRepositoryFixture());
    }

    @Override
    public List<Comment> listByArticleInfo(ArticleType articleType, Long articleId, int page, int pageSize) {
        return delegate.listByArticleInfo(articleType, articleId, page, pageSize)
                ;
    }

    @Override
    public Comment findById(Long id) {
        return delegate.findById(id);
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}