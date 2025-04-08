package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.junit.jupiter.api.Test;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Question;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {Question.class, DefaultQuestionReader.class, QuestionReader.class,
                UserEntity.class,
                IUserEntityRepository.class,
                ParentArticleEntity.class,
                IParentArticleEntityRepository.class,
                ArticleType.class,
                UserRole.class,
                Popularity.class}
)
public class QuestionReaderTest {


}
