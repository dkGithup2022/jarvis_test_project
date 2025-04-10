package com.jarvis.sample.simpleboard.web.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleBase;
import com.jarvis.sample.simpleboard.domain.article.specs.*;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

import java.util.Map;

public class NewArticleMapper {


    private static final Map<ArticleType, Class<? extends ArticleBase>> TYPE_CLASS_MAP = Map.of(
            ArticleType.ARTICLE, Article.class,
            ArticleType.ANNOUNCEMENT, Announcement.class,
            ArticleType.QUESTION, Question.class,
            ArticleType.DISCUSSION, Discussion.class,
            ArticleType.ANSWER, Answer.class,
            ArticleType.DISCUSSION_REPLY, DiscussionReply.class
    );

    public static Class<? extends ArticleBase> getClassByType(ArticleType type) {
        return TYPE_CLASS_MAP.get(type);
    }

    public static ArticleBase newOne(User user, ArticleType type, String title, String content, Long parentId) {
        return switch (type) {
            case ARTICLE -> Article.of(null, user.getUserId(), user.getNickname(), title, content, Popularity.empty(), false);
            case ANNOUNCEMENT -> Announcement.of(null, user.getUserId(), user.getNickname(), title, content, Popularity.empty(), false);
            case QUESTION -> Question.of(null, type, title, content, user.getUserId(), user.getNickname(), Popularity.empty(), false);
            case DISCUSSION -> Discussion.of(null, user.getUserId(), user.getNickname(), title, content, Popularity.empty(), false);
            case ANSWER, DISCUSSION_REPLY -> throw new UnsupportedOperationException("Use child article controller");
        };
    }


}
