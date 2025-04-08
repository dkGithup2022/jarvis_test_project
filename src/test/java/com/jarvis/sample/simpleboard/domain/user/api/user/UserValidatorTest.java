package com.jarvis.sample.simpleboard.domain.user.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_TEST,
    references = { User.class, DefaultUserValidator.class, UserValidator.class }
)
public class UserValidatorTest {

    private IUserEntityRepositoryFixture fixture;
    private DefaultUserValidator userValidator;
    private User testUser;

    @BeforeEach
    void setup() {
        fixture = new IUserEntityRepositoryFixture();
        userValidator = new DefaultUserValidator(fixture);
        testUser = User.of(1L, "validNickname", new HashSet<>());
    }

    @Test
    void canUseNickname_shouldReturnTrue_whenNicknameIsValidAndNotExistsInDb() {
        String nickname = "uniqueNickname";

        // Assume nickname does not exist in the repository
        fixture.clear();
        
        boolean result = userValidator.canUseNickname(nickname);

        Assertions.assertTrue(result);
    }

    @Test
    void canUseNickname_shouldReturnFalse_whenNicknameIsInvalid() {
        String nickname = " ";

        boolean result = userValidator.canUseNickname(nickname);

        Assertions.assertFalse(result);
    }

    @Test
    void canUseNickname_shouldReturnFalse_whenNicknameExistsInDb() {
        String nickname = "existingNickname";

        fixture.saveNickname(nickname);

        boolean result = userValidator.canUseNickname(nickname);

        Assertions.assertFalse(result);
    }

    @Test
    void canUpdate_shouldReturnTrue_whenUserIdMatchesAndExistsInDb() {
        Long requestUserId = 1L;
        fixture.saveUser(testUser);

        boolean result = userValidator.canUpdate(requestUserId, testUser);

        Assertions.assertTrue(result);
    }

    @Test
    void canUpdate_shouldReturnFalse_whenUserIdDoesNotMatch() {
        Long requestUserId = 2L;

        boolean result = userValidator.canUpdate(requestUserId, testUser);

        Assertions.assertFalse(result);
    }

    @Test
    void canUpdate_shouldThrowException_whenUserDoesNotExistInDb() {
        Long requestUserId = 1L;
        User nonExistentUser = User.of(2L, "anotherNickname", new HashSet<>());

        Assertions.assertThrows(RuntimeException.class, () -> {
            userValidator.canUpdate(requestUserId, nonExistentUser);
        });
    }

    @Test
    void canDelete_shouldReturnTrue_whenUserIdMatchesAndExistsInDb() {
        Long requestUserId = 1L;
        fixture.saveUser(testUser);

        boolean result = userValidator.canDelete(requestUserId, testUser);

        Assertions.assertTrue(result);
    }

    @Test
    void canDelete_shouldReturnFalse_whenUserIdDoesNotMatch() {
        Long requestUserId = 2L;

        boolean result = userValidator.canDelete(requestUserId, testUser);

        Assertions.assertFalse(result);
    }

    @Test
    void canDelete_shouldThrowException_whenUserDoesNotExistInDb() {
        Long requestUserId = 1L;
        User nonExistentUser = User.of(2L, "anotherNickname", new HashSet<>());

        Assertions.assertThrows(RuntimeException.class, () -> {
            userValidator.canDelete(requestUserId, nonExistentUser);
        });
    }
} 

// Notes: 
// 1. IUserEntityRepositoryFixture methods like `saveNickname` and `saveUser` are assumed to be implemented for test setup.
// 2. The fixture is assumed to simulate the behavior of a real repository, storing and clearing test data as needed.