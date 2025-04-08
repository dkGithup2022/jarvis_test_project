package com.jarvis.sample.simpleboard.domain.article.api.article;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Article;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Article.class,  ArticleWriter.class }
 )
public class DefaultArticleWriter implements ArticleWriter{
}
