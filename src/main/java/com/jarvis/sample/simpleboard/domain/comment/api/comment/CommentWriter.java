package com.jarvis.sample.simpleboard.domain.comment.api.comment;


import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface CommentWriter {

    /**
     * comment 의 id 가 null 이여야 함 아닐 경우 runtime exception
     * entity 로 변환하여 저장
     * */
    Comment write(Comment comment);


    /**
     * comment 의 id 에 해당하는 문서가 db에 있어야함 아닐경우 runtime exception
     ** entity 로 변환하여 저장
     * */
    Comment update(Comment comment);


}
