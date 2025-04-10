package com.jarvis.sample.simpleboard.domain.article;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.domain.article.api.announcement.AnnouncementValidator;
import com.jarvis.sample.simpleboard.domain.article.api.announcement.AnnouncementWriter;
import com.jarvis.sample.simpleboard.domain.article.api.answer.AnswerValidator;
import com.jarvis.sample.simpleboard.domain.article.api.answer.AnswerWriter;
import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleValidator;
import com.jarvis.sample.simpleboard.domain.article.api.article.ArticleWriter;
import com.jarvis.sample.simpleboard.domain.article.api.discussion.DiscussionValidator;
import com.jarvis.sample.simpleboard.domain.article.api.discussion.DiscussionWriter;
import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DiscussionReplyValidator;
import com.jarvis.sample.simpleboard.domain.article.api.discussionReply.DiscussionReplyWriter;
import com.jarvis.sample.simpleboard.domain.article.api.question.QuestionValidator;
import com.jarvis.sample.simpleboard.domain.article.api.question.QuestionWriter;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ArticleWriterFacade {

    private final ArticleWriter articleWriter;
    private final AnnouncementWriter announcementWriter;
    private final QuestionWriter questionWriter;
    private final DiscussionWriter discussionWriter;
    private final AnswerWriter answerWriter;
    private final DiscussionReplyWriter discussionReplyWriter;

    private final ArticleValidator articleValidator;
    private final AnnouncementValidator announcementValidator;
    private final QuestionValidator questionValidator;
    private final DiscussionValidator discussionValidator;
    private final AnswerValidator answerValidator;
    private final DiscussionReplyValidator discussionReplyValidator;

    private final Map<ArticleType, ArticleWriterBase<? extends ArticleBase>> writerMap;
    private final Map<ArticleType, ArticleValidatorBase<? extends ArticleBase>> validatorMap;

    public ArticleWriterFacade(
            ArticleWriter articleWriter,
            AnnouncementWriter announcementWriter,
            QuestionWriter questionWriter,
            DiscussionWriter discussionWriter,
            AnswerWriter answerWriter,
            DiscussionReplyWriter discussionReplyWriter,
            ArticleValidator articleValidator,
            AnnouncementValidator announcementValidator,
            QuestionValidator questionValidator,
            DiscussionValidator discussionValidator,
            AnswerValidator answerValidator,
            DiscussionReplyValidator discussionReplyValidator
    ) {
        this.articleWriter = articleWriter;
        this.announcementWriter = announcementWriter;
        this.questionWriter = questionWriter;
        this.discussionWriter = discussionWriter;
        this.answerWriter = answerWriter;
        this.discussionReplyWriter = discussionReplyWriter;

        this.articleValidator = articleValidator;
        this.announcementValidator = announcementValidator;
        this.questionValidator = questionValidator;
        this.discussionValidator = discussionValidator;
        this.answerValidator = answerValidator;
        this.discussionReplyValidator = discussionReplyValidator;

        this.writerMap = new HashMap<>();
        this.writerMap.put(ArticleType.ARTICLE, articleWriter);
        this.writerMap.put(ArticleType.ANNOUNCEMENT, announcementWriter);
        this.writerMap.put(ArticleType.QUESTION, questionWriter);
        this.writerMap.put(ArticleType.DISCUSSION, discussionWriter);
        this.writerMap.put(ArticleType.ANSWER, answerWriter);
        this.writerMap.put(ArticleType.DISCUSSION_REPLY, discussionReplyWriter);

        this.validatorMap = new HashMap<>();
        this.validatorMap.put(ArticleType.ARTICLE, articleValidator);
        this.validatorMap.put(ArticleType.ANNOUNCEMENT, announcementValidator);
        this.validatorMap.put(ArticleType.QUESTION, questionValidator);
        this.validatorMap.put(ArticleType.DISCUSSION, discussionValidator);
        this.validatorMap.put(ArticleType.ANSWER, answerValidator);
        this.validatorMap.put(ArticleType.DISCUSSION_REPLY, discussionReplyValidator);
    }

    public <T extends ArticleBase> T write(ArticleType type, ArticleBase article, User user, Class<T> clazz) {
        ArticleWriterBase<T> writer = (ArticleWriterBase<T>) writerMap.get(type);
        ArticleValidatorBase<T> validator = (ArticleValidatorBase<T>) validatorMap.get(type);

        if (!validator.canWrite((T) article, user)) {
            throw new RuntimeException("Cannot write article");
        }

        return writer.write((T) article);
    }

    public <T extends ArticleBase> T update(ArticleType type, ArticleBase article, User user, Class<T> clazz) {
        ArticleWriterBase<T> writer = (ArticleWriterBase<T>) writerMap.get(type);
        ArticleValidatorBase<T> validator = (ArticleValidatorBase<T>) validatorMap.get(type);

        if (!validator.canUpdate((T) article, user)) {
            throw new RuntimeException("Cannot update article");
        }

        return writer.update((T) article);
    }

    public <T extends ArticleBase> void delete(ArticleType type, ArticleBase article, User user) {
        ArticleWriterBase<T> writer = (ArticleWriterBase<T>) writerMap.get(type);
        ArticleValidatorBase<T> validator = (ArticleValidatorBase<T>) validatorMap.get(type);

        if (!validator.canDelete((T) article, user)) {
            throw new RuntimeException("Cannot write article");
        }

        writer.delete(article.getId());
    }
}
