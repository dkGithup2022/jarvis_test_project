package com.jarvis.sample.simpleboard.domain.fixture.article.api.question;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Question;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Question.class, DefaultQuestionReader.class, QuestionReader.class }
)
public class DefaultQuestionReaderFixture implements QuestionReader {
    // TODO: 필요 시 테스트용 목 동작 구현
}