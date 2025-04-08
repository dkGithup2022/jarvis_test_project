package com.jarvis.sample.simpleboard.domain.fixture.article.api.answer;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Answer.class, DefaultAnswerWriter.class, AnswerWriter.class }
)
public class DefaultAnswerWriterFixture implements AnswerWriter {
    // TODO: 필요 시 테스트용 목 동작 구현
}