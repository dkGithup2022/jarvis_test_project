package com.jarvis.sample.simpleboard.domain.article;

import com.jarvis.sample.simpleboard.domain.user.specs.User;

public interface ArticleWriterBase<T> {

    /*
     * 각자 필요한 repository 에 맞는 entity 로 변환하여 저장합니다.
     *
     * article의 id 가 있어선 안됩니다.
     * 있다면 runtime exception 을 반환합니다.
     * */
    T write(T article);

    /*
     * id를 토대로 article 을 조회 후에 Article 도메인 객체의 스펙에 맞추어 테이블에 값을 저장합니다.
     * article가 테이블 내에 존재해야 합니다.
     * 없다면 runtime exception 을 반환합니다.
     * */
    T update(T article);

    /*
    * 문서의 delete flag 를 true 로 바꿉니다.
    *
    * 그후 db 에 다시 저장합니다.
    * */
    void delete(Long id);
}