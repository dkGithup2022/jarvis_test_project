package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.answer;

import com.jarvis.sample.simpleboard.domain.article.api.answer.AnswerReader;
import com.jarvis.sample.simpleboard.domain.article.api.answer.DefaultAnswerReader;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_FIXTURE,
    references = { Answer.class, DefaultAnswerReader.class, AnswerReader.class }
)
public class DefaultAnswerReaderFixture implements AnswerReader {
    @Override
    public Answer read(Long articleId) {
        return null;
    }
    // TODO: 필요 시 테스트용 목 동작 구현
}