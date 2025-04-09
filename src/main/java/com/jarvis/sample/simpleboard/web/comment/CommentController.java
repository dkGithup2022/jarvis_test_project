package com.jarvis.sample.simpleboard.web.comment;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentReader;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentValidator;
import com.jarvis.sample.simpleboard.domain.comment.api.comment.CommentWriter;
import com.jarvis.sample.simpleboard.domain.comment.specs.Comment;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserReader;
import com.jarvis.sample.simpleboard.web.SessionHolder;
import com.jarvis.sample.simpleboard.web.article.dto.ArticleWriteRequest;
import com.jarvis.sample.simpleboard.web.comment.dto.CommentWriteRequest;
import com.jarvis.sample.simpleboard.web.dto.SuccessApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {

    private final SessionHolder sessionHolder;
    private final UserReader userReader;

    private final CommentReader commentReader;
    private final CommentWriter commentWriter;
    private final CommentValidator commentValidator;


    @PostMapping("/write")
    public ResponseEntity<SuccessApiResponse<Comment>> write(CommentWriteRequest request) {
        var user = userReader.findById(sessionHolder.requestUserId());
        var comment = Comment.of(null, request.articleType(), request.articleId(), request.content(), request.parentCommentId(), null, null, null, false);

        if (!commentValidator.canWrite(user, comment))
            throw new RuntimeException("못쓰는 댓글");

        var saved = commentWriter.write(comment);

        return ResponseEntity.ok(SuccessApiResponse.success(saved));

    }

    public ResponseEntity<SuccessApiResponse> delete(CommentDeleteRequest request) {

        // comment 에 대한 단건 조회 api 구현 필요 - 스킵
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<SuccessApiResponse<List<Comment>>> listByArticleInfo(@RequestParam ArticleType type, @RequestParam Long articleId, @RequestParam(required = false) int page) {
        var list = commentReader.listByArticleInfo(type, articleId, page, 100);
        return ResponseEntity.ok(SuccessApiResponse.success(list));
    }
}
