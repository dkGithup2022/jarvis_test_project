package com.jarvis.sample.simpleboard.domain.article;

import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;

public class PopularityMapper {
    public static PopularityEmbeddable toEmbeddable(Popularity popularity) {
        return new PopularityEmbeddable(
                popularity.views(),
                popularity.likes(),
                popularity.dislikes(),
                popularity.comments()
        );
    }

    public static Popularity toRead(PopularityEmbeddable popularityEmbeddable) {
        return new Popularity(
                popularityEmbeddable.getViews(),
                popularityEmbeddable.getLikes(),
                popularityEmbeddable.getDislikes(),
                popularityEmbeddable.getComments()
        );
    }
}
