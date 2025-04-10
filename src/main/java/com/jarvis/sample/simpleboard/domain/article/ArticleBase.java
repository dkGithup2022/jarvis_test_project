package com.jarvis.sample.simpleboard.domain.article;

public interface ArticleBase {
    Long getId();

    void updateTitle(String title);
    void updateContent(String content);
}
