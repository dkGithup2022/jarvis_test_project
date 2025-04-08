package com.jarvis.sample.simpleboard.domain.fixture.article.api.article;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Article.class, DefaultArticleWriter.class, ArticleWriter.class }
)
public class DefaultArticleWriterFixture implements ArticleWriter {
    // TODO: 필요 시 테스트용 목 동작 구현
}