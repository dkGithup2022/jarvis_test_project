package com.jarvis.sample.simpleboard.domain.fixture.domain.article.api.answer;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleWriterBase;
import com.jarvis.sample.simpleboard.domain.article.api.answer.AnswerWriter;
import com.jarvis.sample.simpleboard.domain.article.api.answer.DefaultAnswerWriter;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.domain.article.specs.Answer;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_FIXTURE,
        references = {
                Answer.class, DefaultAnswerWriter.class, AnswerWriter.class,

                ArticleWriterBase.class,

                ChildArticleEntity.class,
                IChildArticleEntityRepository.class,

                Popularity.class,
                PopularityEmbeddable.class,

                IUserEntityRepository.class,
                ArticleType.class,

                UserRole.class,
        }
)
public class DefaultAnswerWriterFixture implements AnswerWriter {
    @Override
    public Answer write(Answer article) {
        return null;
    }

    @Override
    public Answer update(Answer article) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    // TODO: 필요 시 테스트용 목 동작 구현
}