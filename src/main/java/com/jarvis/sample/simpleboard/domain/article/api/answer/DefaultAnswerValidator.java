package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Answer.class,  AnswerValidator.class }
 )
public class DefaultAnswerValidator implements AnswerValidator{
}
