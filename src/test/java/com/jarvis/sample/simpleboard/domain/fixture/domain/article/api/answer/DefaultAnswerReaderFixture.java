package com.jarvis.sample.simpleboard.domain.fixture.article.api.answer;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Answer.class, DefaultAnswerReader.class, AnswerReader.class }
)
public class DefaultAnswerReaderFixture implements AnswerReader {
    // TODO: 필요 시 테스트용 목 동작 구현
}