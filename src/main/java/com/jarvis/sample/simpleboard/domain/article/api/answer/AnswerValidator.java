package com.jarvis.sample.simpleboard.domain.article.api.answer;


import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface AnswerValidator extends ArticleValidatorBase<Answer> {
}
