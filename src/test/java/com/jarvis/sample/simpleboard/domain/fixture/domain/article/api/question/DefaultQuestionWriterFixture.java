package com.jarvis.sample.simpleboard.domain.fixture.article.api.question;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Question;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Question.class, DefaultQuestionWriter.class, QuestionWriter.class }
)
public class DefaultQuestionWriterFixture implements QuestionWriter {
    // TODO: 필요 시 테스트용 목 동작 구현
}