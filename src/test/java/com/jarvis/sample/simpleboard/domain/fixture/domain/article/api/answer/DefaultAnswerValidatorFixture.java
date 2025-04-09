package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.answer;

import com.jarvis.sample.simpleboard.domain.article.api.answer.AnswerValidator;
import com.jarvis.sample.simpleboard.domain.article.api.answer.DefaultAnswerValidator;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Answer.class, DefaultAnswerValidator.class, AnswerValidator.class }
)
public class DefaultAnswerValidatorFixture implements AnswerValidator {
    @Override
    public boolean canWrite(Answer article, User user) {
        return false;
    }

    @Override
    public boolean canUpdate(Answer article, User user) {
        return false;
    }

    @Override
    public boolean canDelete(Answer article, User user) {
        return false;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}