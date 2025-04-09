package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.article;

import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleWriter;
import com.jarvis.sample.simpleboard.domain.article.api.article.DefaultArticleWriter;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Article.class, DefaultArticleWriter.class, ArticleWriter.class }
)
public class DefaultArticleWriterFixture implements ArticleWriter {
    @Override
    public Article write(Article article) {
        return null;
    }

    @Override
    public Article update(Article article) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    // TODO: 필요 시 테스트용 목 동작 구현
}