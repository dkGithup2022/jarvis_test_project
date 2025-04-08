package com.jarvis.sample.simpleboard.domain.article.api.answer;



import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface AnswerReader extends ArticleReaderBase<Answer> {
}
