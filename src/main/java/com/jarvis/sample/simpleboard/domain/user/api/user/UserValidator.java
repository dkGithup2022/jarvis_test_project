package com.jarvis.sample.simpleboard.domain.user.api.user;


import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface UserValidator {

    /*
    [설명]
    * 1. user 스펙에 nickname 이 올바르게 사용할 수 있는지.
    * 2. db 에 같은 nickname 이 있는지 검사한다.
    * */
    Boolean canUseNickname(String nickname);

    /*
    [설명]
    생성된 user 객체의 id 가 reqesterUserID 와 같은지 확인.
    user.userId 에 해당하는 유저가 db 에 존재하는지 확인.
    없다면 runtimeexception 을 반환한다.
    간단한 로직
     */
    Boolean canUpdate(Long requestUserId, User user);

    /*
    [설명]
    생성된 user 객체의 id 가 reqesterUserID 와 같은지 확인.
    user.userId 에 해당하는 유저가 db 에 존재하는지 확인.
    없다면 runtimeexception 을 반환한다.
    간단한 로직
     */
    Boolean canDelete(Long requestUserId, User user);

}
