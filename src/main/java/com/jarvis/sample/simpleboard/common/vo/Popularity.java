package com.jarvis.sample.simpleboard.common.vo;

public record Popularity(
        int views,
        int likes,
        int dislikes,
        int comments
) {

    public static Popularity of(int views, int likes, int dislikes, int comments) {
        return new Popularity(views, likes, dislikes, comments);
    }

    public static Popularity empty() {
        return new Popularity(0, 0, 0, 0);
    }

    public Popularity increaseViews() {
        return new Popularity(views + 1, likes, dislikes, comments);
    }

    public Popularity increaseLikes() {
        return new Popularity(views, likes + 1, dislikes, comments);
    }

    public Popularity increaseDislikes() {
        return new Popularity(views, likes, dislikes + 1, comments);
    }

    public Popularity increaseComments() {
        return new Popularity(views, likes, dislikes, comments + 1);
    }
}
