package com.jarvis.sample.simpleboard.domain.article.api.discussion;

import com.jarvis.sample.simpleboard.common.type.ArticleType;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.common.vo.Popularity;
import com.jarvis.sample.simpleboard.domain.article.ArticleReaderBase;
import com.jarvis.sample.simpleboard.domain.article.specs.Discussion;
import com.jarvis.sample.simpleboard.infra.article.ParentArticleEntity;
import com.jarvis.sample.simpleboard.infra.article.api.IParentArticleEntityRepository;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { Discussion.class, DefaultDiscussionReader.class, DiscussionReader.class, ArticleReaderBase.class,
            UserEntity.class, IUserEntityRepository.class,
            ParentArticleEntity.class, IParentArticleEntityRepository.class,
            ArticleType.class, UserRole.class, Popularity.class }
)
public class DiscussionReaderTest {

    private IParentArticleEntityRepository parentArticleEntityRepository;
    private IUserEntityRepository userEntityRepository;
    private DefaultDiscussionReader discussionReader;

    @BeforeEach
    void setup() {
        parentArticleEntityRepository = mock(IParentArticleEntityRepository.class);
        userEntityRepository = mock(IUserEntityRepository.class);
        discussionReader = new DefaultDiscussionReader(parentArticleEntityRepository, userEntityRepository);
    }

    @Test
    void read_shouldReturnDiscussion_whenValidArticleId() {
        ParentArticleEntity articleEntity = ParentArticleEntity.of(
                1L, ArticleType.DISCUSSION, "Title", "Content", 1L,
                new PopularityEmbeddable(5, 3, 0, 2), false);
        UserEntity userEntity = UserEntity.of("password", "nickname", Set.of(UserRole.USER));

        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(articleEntity));
        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        Discussion discussion = discussionReader.read(1L);

        assertNotNull(discussion);
        assertEquals("Title", discussion.getTitle());
        assertEquals("nickname", discussion.getAuthorNickname());
    }

    @Test
    void read_shouldThrowException_whenArticleNotFound() {
        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> discussionReader.read(1L));

        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    void read_shouldThrowException_whenInvalidArticleType() {
        ParentArticleEntity articleEntity = ParentArticleEntity.of(
                1L, ArticleType.NORMAL, "Title", "Content", 1L,
                new PopularityEmbeddable(5, 3, 0, 2), false);

        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(articleEntity));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> discussionReader.read(1L));

        assertEquals("Invalid article type", exception.getMessage());
    }

    @Test
    void read_shouldThrowException_whenUserNotFound() {
        ParentArticleEntity articleEntity = ParentArticleEntity.of(
                1L, ArticleType.DISCUSSION, "Title", "Content", 1L,
                new PopularityEmbeddable(5, 3, 0, 2), false);

        when(parentArticleEntityRepository.findById(1L)).thenReturn(Optional.of(articleEntity));
        when(userEntityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> discussionReader.read(1L));

        assertEquals("User not found", exception.getMessage());
    }
}