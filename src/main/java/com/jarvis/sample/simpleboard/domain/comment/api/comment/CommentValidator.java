package com.jarvis.sample.simpleboard.domain.comment.api.comment;



import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface CommentValidator {

    /*
    * user 가  null 이 아니고,
    * userRoles 에 user, system, admin 세개 중 하나라도 포함되어 있어야 함.
    * db에 유저가 존재해야함.
    *
    * */
    Boolean canWrite(User user, Comment comment);

    /*
    * user id 가 comment 의 user id 와 동일.
    * db dp user, comment 가 둘 다 존재 해야함.
    * */
    Boolean canUpdate(User user, Comment comment);
}
