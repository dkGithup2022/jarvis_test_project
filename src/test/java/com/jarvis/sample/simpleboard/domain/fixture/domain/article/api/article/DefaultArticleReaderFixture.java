package com.jarvis.sample.simpleboard.domain.fixture.article.api.article;

import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleReader;
import com.jarvis.sample.simpleboard.domain.article.api.article.DefaultArticleReader;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Article.class, DefaultArticleReader.class, ArticleReader.class }
)
public class DefaultArticleReaderFixture implements ArticleReader {
    @Override
    public Article read(Long articleId) {
        return null;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}