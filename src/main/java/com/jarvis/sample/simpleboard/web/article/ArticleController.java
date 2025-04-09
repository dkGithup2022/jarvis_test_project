package com.jarvis.sample.simpleboard.web.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.article.ArticleBase;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderFacade;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterFacade;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserReader;
import com.jarvis.sample.simpleboard.web.SessionHolder;
import com.jarvis.sample.simpleboard.web.article.dto.ArticleUpdateRequest;
import com.jarvis.sample.simpleboard.web.article.dto.ArticleWriteRequest;
import com.jarvis.sample.simpleboard.web.dto.SuccessApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleWriterFacade writerFacade;
    private final ArticleReaderFacade readerFacade;
    private final SessionHolder sessionHolder;
    private final UserReader userReader;

    @PostMapping("/write")
    public ResponseEntity<SuccessApiResponse> write(ArticleWriteRequest request) {

        // 로그인 유저 - 빈 도메인 생성
        var user = userReader.findById(sessionHolder.requestUserId());
        var createdArticle = NewArticleMapper.newOne(user, request.type(), request.title(), request.content(), request.parentId());

        // 타입 정보 가져오기 - 저장
        var clazz = NewArticleMapper.getClassByType(request.type());
        var saved = writerFacade.write(request.type(), createdArticle, user, clazz);

        return ResponseEntity.ok(SuccessApiResponse.success(saved));
    }

    @PostMapping("/update")
    public ResponseEntity<SuccessApiResponse> update(ArticleUpdateRequest request) {

        // 로그인 유저
        var user = userReader.findById(sessionHolder.requestUserId());

        // 타입 정보 가져오기 - 업데이트 도메인 생성
        var clazz = NewArticleMapper.getClassByType(request.type());
        var article = readerFacade.read(request.type(), request.articleId(), clazz);
        article.updateContent(request.content());
        article.updateTitle(request.title());

        // 저장
        var updated = writerFacade.update(request.type(), article, user, clazz);

        return ResponseEntity.ok(SuccessApiResponse.success(updated));
    }


    @GetMapping("/read")
    public ResponseEntity<SuccessApiResponse> read(@RequestParam Long id, @RequestParam ArticleType type) {
        // 로그인 유저
        var user = userReader.findById(sessionHolder.requestUserId());

        // 타입 정보 가져오기
        var clazz = NewArticleMapper.getClassByType(type);
        var article = readerFacade.read(type, id, clazz);
        return ResponseEntity.ok(SuccessApiResponse.success(article));
    }
}
