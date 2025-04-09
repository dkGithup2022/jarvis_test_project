package com.jarvis.sample.simpleboard.domain.user.api.user;


import com.jarvis.sample.simpleboard.domain.article.ArticleBase;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface UserWriter {

    /*
     * [설명]
     *    새로운 유저 데이터를  entity 로 매핑하여 save 한다.
     *     입력 받는 user 객체의 id 는 null 이여야 한다 아닐 시 runtimeException
     *
     * 유저 엔티티를 만들어 저장한다.
     * UserRole 을 Set.of(User) 로서 지정하여 저장.
     * password 는 spring 에서 사용할 수 있는 적절한 encoder 로 인코딩하여 저장한다.
     */
    User createUser(String nickname, String password);


    /*
     * [설명]
     *    새로운 유저 데이터를  entity 로 매핑하여 save 한다.
     *    1. user 정보를 조회하여 존재하는지 확인한다. 없을 경우, runtimeexception 을 반환한다.
     *    2. user.userId 에 해당하는 유저가 db 에 존재하는지 확인. 없다면 runtimeexception 을 반환한다.
     *
     *   새로운 User 값으로 매핑하여  entity 를 다시 save  한다. 같은 id 에 대해 save
     */
    User updateUser(User user);


    /*
     * [설명]
     *    새로운 유저 데이터를  entity 로 매핑하여 save 한다.
     *    1. user 정보를 조회하여 존재하는지 확인한다. 없을 경우, runtimeexception 을 반환한다.
     *    2. user.userId 에 해당하는 유저가 db 에 존재하는지 확인. 없다면 runtimeexception 을 반환한다.
     *
     *   새로 User id 에 대한 entity 를 찾아서 delete flag 를 true 로 변경한다.
     */
    User deleteUserInfo(User user);
}
