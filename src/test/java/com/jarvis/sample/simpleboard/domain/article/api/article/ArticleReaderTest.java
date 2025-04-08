package com.jarvis.sample.simpleboard.domain.article.api.article;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Article.class, DefaultArticleReader.class, ArticleReader.class }
)
public class ArticleReaderTest {


}
