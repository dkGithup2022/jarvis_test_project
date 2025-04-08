package com.jarvis.sample.simpleboard.web.comment;

import com.jarvis.sample.simpleboard.web.article.dto.ArticleWriteRequest;
import com.jarvis.sample.simpleboard.web.comment.dto.CommentWriteRequest;
import com.jarvis.sample.simpleboard.web.dto.SuccessApiResponse;
import org.springframework.http.ResponseEntity;

public class CommentController {


    public ResponseEntity<SuccessApiResponse> write(CommentWriteRequest request) {
        // 구현 필요
        return null;
    }

    public ResponseEntity<SuccessApiResponse> update(CommentWriteRequest request){
        return null;
    }

    public ResponseEntity<SuccessApiResponse> listByArticleInfo(){
        return null;
    }
}
