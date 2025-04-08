package com.jarvis.sample.simpleboard.domain.article.api.answer;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Answer.class, DefaultAnswerValidator.class, AnswerValidator.class }
)
public class AnswerValidatorTest {


}
