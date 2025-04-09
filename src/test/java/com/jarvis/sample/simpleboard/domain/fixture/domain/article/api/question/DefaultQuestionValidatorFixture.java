package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.question;

import com.jarvis.sample.simpleboard.domain.article.api.question.DefaultQuestionValidator;
import com.jarvis.sample.simpleboard.domain.article.api.question.QuestionValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Question;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Question.class, DefaultQuestionValidator.class, QuestionValidator.class }
)
public class DefaultQuestionValidatorFixture implements QuestionValidator {
    @Override
    public boolean canWrite(Question article, User user) {
        return false;
    }

    @Override
    public boolean canUpdate(Question article, User user) {
        return false;
    }

    @Override
    public boolean canDelete(Question article, User user) {
        return false;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}