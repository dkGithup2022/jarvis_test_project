package com.jarvis.sample.simpleboard.domain.article;

public interface ArticleReaderBase<R> {

    /*
    * article Id 를 통해서 문서에 접근 후 도메인 객체로 매핑하여 반환합니다.
    *
    * articleType 별로 사용해야 하는 repo 가 다릅니다.
    * - article, announcement 인 경우, IArticleEntityRepository
    * - question, discussion 인 경우, IParentArticleEntityRepository
    * - answer, discussionReply 인 경우, IChildArticleEntityRepository
    *
    * articleEntity 의 authorId 를 토대로 user 테이블에서 author nickname 을 가져옵니다.
    * */
    R read(Long articleId);
}