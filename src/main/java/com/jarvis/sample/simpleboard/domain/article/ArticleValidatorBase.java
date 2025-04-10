package com.jarvis.sample.simpleboard.domain.article;

import com.jarvis.sample.simpleboard.domain.user.specs.User;

public interface ArticleValidatorBase<T extends ArticleBase> {

    /*
    * UserRole 가 null 이거나 비어있지 않으면 작성할 수있습니다.
    * article에 id 가 명시되어 있는 경우, 사용할 수 없습니다.
    * */
    boolean canWrite(T article, User user);

    /*
    * article 의 author id 와 userID 가 일치해야 합니다.
    * article 과 user 가 테이블 내에 존재해야 합니다.
    * */
    boolean canUpdate(T article, User user);


    /*
     * article 의 author id 와 userID 가 일치해야 합니다.
     * article 과 user 가 테이블 내에 존재해야 합니다.
     * */
    boolean canDelete(T article, User user);
}