package com.jarvis.sample.simpleboard.domain.article.api.question;

import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Question;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Question.class, DefaultQuestionValidator.class, QuestionValidator.class }
)
public class QuestionValidatorTest {


}
