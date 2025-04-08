package com.jarvis.sample.simpleboard.domain.article.api.answer;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

 @JarvisMeta(
     fileType = FileType.DOMAIN_API_IMPL,
     references = { Answer.class,  AnswerWriter.class ,
             ChildArticleEntity.class,
             IChildArticleEntityRepository.class,
             Popularity.class,
             PopularityEmbeddable.class,
             IUserEntityRepository.class,
             ArticleType.class

     }
 )
public class DefaultAnswerWriter implements AnswerWriter{
}
