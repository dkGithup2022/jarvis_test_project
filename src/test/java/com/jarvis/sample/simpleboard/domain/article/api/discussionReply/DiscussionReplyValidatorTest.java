package com.jarvis.sample.simpleboard.domain.article.api.discussionReply;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleValidatorBase;
import com.jarvis.sample.simpleboard.domain.article.specs.DiscussionReply;
import com.jarvis.sample.simpleboard.fixture.infra.article.childArticle.IChildArticleEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.article.ChildArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.PopularityEmbeddable;
import com.jarvis.sample.simpleboard.infra.article.api.IChildArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.api.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { DiscussionReply.class, DefaultDiscussionReplyValidator.class,
            DiscussionReplyValidator.class,
            ArticleValidatorBase.class,
            ChildArticleEntity.class,
            IChildArticleEntityRepository.class,
            IChildArticleEntityRepositoryFixture.class,
            Popularity.class,
            PopularityEmbeddable.class,
            IUserEntityRepository.class,
            IUserEntityRepositoryFixture.class,
            ArticleType.class
    }
)
public class DiscussionReplyValidatorTest {

    private IChildArticleEntityRepositoryFixture childArticleFixture;
    private IUserEntityRepositoryFixture userFixture;
    private DefaultDiscussionReplyValidator validator;

    @BeforeEach
    void setup() {
        childArticleFixture = new IChildArticleEntityRepositoryFixture();
        userFixture = new IUserEntityRepositoryFixture();
        validator = new DefaultDiscussionReplyValidator(childArticleFixture, userFixture);
    }

    @Test
    void canWrite_shouldReturnTrueWhenUserRoleIsNotNullAndArticleIdIsNull() {
        UserEntity user = new UserEntity(1L, "user", "role");
        DiscussionReply article = DiscussionReply.of(null, 1L, "author", "title", "content", Popularity.empty(), 1L, 1, false);

        assertTrue(validator.canWrite(article, user));
    }

    @Test
    void canWrite_shouldReturnFalseWhenUserRoleIsNull() {
        UserEntity user = new UserEntity(1L, "user", null);
        DiscussionReply article = DiscussionReply.of(null, 1L, "author", "title", "content", Popularity.empty(), 1L, 1, false);

        assertFalse(validator.canWrite(article, user));
    }

    @Test
    void canUpdate_shouldReturnTrueWhenArticleAndUserExistAndAuthorIdMatches() {
        DiscussionReply article = DiscussionReply.of(1L, 1L, "author", "title", "content", Popularity.empty(), 1L, 1, false);
        UserEntity user = new UserEntity(1L, "user", "role");
        childArticleFixture.save(ChildArticleEntity.of(1L, 1L, ArticleType.DISCUSSION_REPLY, "title", "content", new PopularityEmbeddable(), 1L, 1, false));
        userFixture.save(user);

        assertTrue(validator.canUpdate(article, user));
    }

    @Test
    void canUpdate_shouldReturnFalseWhenArticleIsNull() {
        UserEntity user = new UserEntity(1L, "user", "role");

        assertFalse(validator.canUpdate(null, user));
    }

    @Test
    void canUpdate_shouldReturnFalseWhenUserIsNull() {
        DiscussionReply article = DiscussionReply.of(1L, 1L, "author", "title", "content", Popularity.empty(), 1L, 1, false);

        assertFalse(validator.canUpdate(article, null));
    }

    @Test
    void canUpdate_shouldReturnFalseWhenAuthorIdDoesNotMatch() {
        DiscussionReply article = DiscussionReply.of(1L, 2L, "author", "title", "content", Popularity.empty(), 1L, 1, false);
        UserEntity user = new UserEntity(1L, "user", "role");
        childArticleFixture.save(ChildArticleEntity.of(1L, 2L, ArticleType.DISCUSSION_REPLY, "title", "content", new PopularityEmbeddable(), 1L, 1, false));
        userFixture.save(user);

        assertFalse(validator.canUpdate(article, user));
    }

    @Test
    void canDelete_shouldBehaveSameAsCanUpdate() {
        DiscussionReply article = DiscussionReply.of(1L, 1L, "author", "title", "content", Popularity.empty(), 1L, 1, false);
        UserEntity user = new UserEntity(1L, "user", "role");
        childArticleFixture.save(ChildArticleEntity.of(1L, 1L, ArticleType.DISCUSSION_REPLY, "title", "content", new PopularityEmbeddable(), 1L, 1, false));
        userFixture.save(user);

        assertTrue(validator.canDelete(article, user));
    }
}