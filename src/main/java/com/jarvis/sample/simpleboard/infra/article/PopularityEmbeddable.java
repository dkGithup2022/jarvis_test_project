package com.jarvis.sample.simpleboard.infra.article;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PopularityEmbeddable {
    private int views;
    private int likes;
    private int dislikes;
    private int comments;
}
