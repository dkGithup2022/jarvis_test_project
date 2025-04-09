package com.jarvis.sample.simpleboard.domain.article.api.question;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.article.parentArticle.IParentArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.domain.article.specs.Question;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@JarvisMeta(
        fileType = FileType.DOMAIN_API_TEST,
        references = {
                Question.class,
                DefaultQuestionReader.class,
                QuestionReader.class,

                UserEntity.class,
                IUserEntityRepository.class,

                ParentArticleEntity.class,
                IParentArticleEntityRepository.class,

                User.class,
                PopularityEmbeddable.class,
                FakeSetter.class,
                ArticleType.class,
                UserRole.class,
                Popularity.class}
)
public class QuestionReaderTest {

    private IParentArticleEntityRepositoryFixture parentArticleEntityRepositoryFixture;
    private IUserEntityRepositoryFixture userEntityRepositoryFixture;
    private DefaultQuestionReader questionReader;

    @BeforeEach
    void setup() {
        parentArticleEntityRepositoryFixture = new IParentArticleEntityRepositoryFixture();
        userEntityRepositoryFixture = new IUserEntityRepositoryFixture();
        questionReader = new DefaultQuestionReader(parentArticleEntityRepositoryFixture, userEntityRepositoryFixture);
    }

    @Test
    void read_shouldReturnQuestionWhenArticleAndAuthorExist() {
        ParentArticleEntity articleEntity = ParentArticleEntity.of(
                1L, ArticleType.QUESTION, "Title", "Content", 1L, null, false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        parentArticleEntityRepositoryFixture.save(articleEntity);
        userEntityRepositoryFixture.save(userEntity);

        Question question = questionReader.read(1L);

        assertNotNull(question);
        assertEquals(articleEntity.getId(), question.getId());
        assertEquals(userEntity.getNickname(), question.getAuthorNickname());
    }

    @Test
    void read_shouldThrowExceptionWhenArticleNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> questionReader.read(1L));
        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void read_shouldThrowExceptionWhenAuthorNotFound() {
        ParentArticleEntity articleEntity = ParentArticleEntity.of(
                1L, ArticleType.QUESTION, "Title", "Content", 1L, null, false);

        parentArticleEntityRepositoryFixture.save(articleEntity);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> questionReader.read(1L));
        assertEquals("Author not found", exception.getMessage());
    }
}

// Note: The IUserEntityRepositoryFixture implementation needs to be created similarly to IParentArticleEntityRepositoryFixture
// with appropriate methods for storing and retrieving UserEntity instances for testing purposes.