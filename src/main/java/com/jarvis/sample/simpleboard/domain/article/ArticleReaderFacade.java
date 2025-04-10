package com.jarvis.sample.simpleboard.domain.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.article.api.announcement.AnnouncementReader;
import com.jarvis.sample.simpleboard.domain.article.api.answer.AnswerReader;
import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleReader;
import com.jarvis.sample.simpleboard.domain.article.api.discussion.DiscussionReader;
import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DiscussionReplyReader;
import com.jarvis.sample.simpleboard.domain.article.api.question.QuestionReader;
import com.jarvis.sample.simpleboard.domain.article.specs.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ArticleReaderFacade {

    private final ArticleReader articleReader;
    private final AnnouncementReader announcementReader;
    private final QuestionReader questionReader;
    private final DiscussionReader discussionReader;
    private final AnswerReader answerReader;
    private final DiscussionReplyReader discussionReplyReader;

    Map<ArticleType, ArticleReaderBase> readerMap;

    public ArticleReaderFacade(ArticleReader articleReader, AnnouncementReader announcementReader, QuestionReader questionReader, DiscussionReader discussionReader, AnswerReader answerReader, DiscussionReplyReader discussionReplyReader) {
        this.articleReader = articleReader;
        this.announcementReader = announcementReader;
        this.questionReader = questionReader;
        this.discussionReader = discussionReader;
        this.answerReader = answerReader;
        this.discussionReplyReader = discussionReplyReader;

        this.readerMap = new HashMap<>();
        readerMap.put(ArticleType.ARTICLE, articleReader);
        readerMap.put(ArticleType.ANNOUNCEMENT, announcementReader);
        readerMap.put(ArticleType.QUESTION, questionReader);
        readerMap.put(ArticleType.DISCUSSION, discussionReader);
        readerMap.put(ArticleType.ANSWER, answerReader);
        readerMap.put(ArticleType.DISCUSSION_REPLY, discussionReplyReader);

    }

    public <T extends ArticleBase> T read(ArticleType type, Long id, Class<T> clazz) {
        var reader = readerMap.get(type);
        if (reader == null) {
            throw new RuntimeException("type 에 해당하는 reader 없음");
        }

        var read = reader.read(id);

        return clazz.cast(read);
    }
}
