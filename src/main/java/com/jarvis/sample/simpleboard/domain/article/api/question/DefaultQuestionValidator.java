package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Question.class,  QuestionValidator.class }
 )
public class DefaultQuestionValidator implements QuestionValidator{
}
