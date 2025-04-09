package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.article;

import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleValidator;
import com.jarvis.sample.simpleboard.domain.article.api.article.DefaultArticleValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Article;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Article.class, DefaultArticleValidator.class, ArticleValidator.class }
)
public class DefaultArticleValidatorFixture implements ArticleValidator {
    @Override
    public boolean canWrite(Article article, User user) {
        return false;
    }

    @Override
    public boolean canUpdate(Article article, User user) {
        return false;
    }

    @Override
    public boolean canDelete(Article article, User user) {
        return false;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}