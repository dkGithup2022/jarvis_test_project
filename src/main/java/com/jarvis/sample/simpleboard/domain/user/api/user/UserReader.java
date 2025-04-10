package com.jarvis.sample.simpleboard.domain.user.api.user;


import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface UserReader {

    /*
    [설명]
    간단한 User 조회 api
    repo 의 값을 읽은 뒤,entity 를 데이터 스펙으로 mapping 해서 리턴한다.
    조회 결과가 없는 경우, null 을 리턴, 커스텀한 exception 은 없다.
    */
    User findById(Long userId);


    /*
    [설명]
    간단한 User 조회 api
    repo 의 값을 읽은 뒤,entity 를 데이터 스펙으로 mapping 해서 리턴한다.
    조회 결과가 없는 경우, null 을 리턴, 커스텀한 exception 은 없다.

    nickname 에 해당하는 유저가 2명 이상인 경우 RuntimeException();
    */
    User findByNickname(String nickname);
}
