package com.jarvis.sample.simpleboard.domain.user.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { User.class, DefaultUserReader.class, UserReader.class }
)
public class UserReaderTest {

    private IUserEntityRepositoryFixture fixture;
    private DefaultUserReader userReader;

    @BeforeEach
    void setup() {
        fixture = new IUserEntityRepositoryFixture();
        userReader = new DefaultUserReader(fixture);
    }

    @Test
    void findById_shouldReturnUserWhenUserExists() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity(userId, "JohnDoe", Set.of(UserRole.USER));
        fixture.save(userEntity);

        User result = userReader.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("JohnDoe", result.getNickname());
        assertEquals(Set.of(UserRole.USER), result.getUserRole());
    }

    @Test
    void findById_shouldReturnNullWhenUserDoesNotExist() {
        Long userId = 1L;

        User result = userReader.findById(userId);

        assertNull(result);
    }
}

// Note: IUserEntityRepositoryFixture should implement save and findById methods
// for the test to work correctly.