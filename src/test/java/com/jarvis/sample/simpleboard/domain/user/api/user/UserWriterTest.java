package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.fixture.infra.user.user.IUserEntityRepositoryFixture;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
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
        references = {User.class, DefaultUserWriter.class, UserWriter.class,
                IUserEntityRepositoryFixture.class,
                IUserEntityRepository.class,
                User.class,
                UserEntity.class,
                UserRole.class
        }
)
public class UserWriterTest {

    private IUserEntityRepository repository;
    private PasswordEncoder passwordEncoder;
    private DefaultUserWriter userWriter;

    @BeforeEach
    void setup() {
        repository = mock(IUserEntityRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userWriter = new DefaultUserWriter(repository, passwordEncoder);
    }

    @Test
    void createUser_shouldSaveNewUser_whenDataIsValid() {
        String nickname = "testNickname";
        String password = "testPassword";
        String encodedPassword = "encodedPassword";
        UserEntity userEntity = UserEntity.of(encodedPassword, nickname, Set.of(UserRole.USER));
        UserEntity savedEntity = UserEntity.of(encodedPassword, nickname, Set.of(UserRole.USER));
        FakeSetter.setField(savedEntity, "id", 1L);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(repository.save(any(UserEntity.class))).thenReturn(savedEntity);

        User result = userWriter.createUser(nickname, password);

        assertNotNull(result.getUserId());
        assertEquals(nickname, result.getNickname());
        verify(passwordEncoder, times(1)).encode(password);
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_shouldThrowException_whenNicknameIsNull() {
        String password = "testPassword";

        assertThrows(RuntimeException.class, () -> userWriter.createUser(null, password));
    }

    @Test
    void createUser_shouldThrowException_whenPasswordIsNull() {
        String nickname = "testNickname";

        assertThrows(RuntimeException.class, () -> userWriter.createUser(nickname, null));
    }

    @Test
    void updateUser_shouldUpdateUser_whenUserExists() {
        User user = User.of(1L, "updatedNickname", Set.of(UserRole.USER));
        UserEntity existingEntity = UserEntity.of("encodedPassword", "currentNickname", Set.of(UserRole.USER));
        FakeSetter.setField(existingEntity, "id", 1L);
        UserEntity updatedEntity = UserEntity.of("encodedPassword", "updatedNickname", Set.of(UserRole.USER));
        FakeSetter.setField(updatedEntity, "id", 1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(UserEntity.class))).thenReturn(updatedEntity);

        User result = userWriter.updateUser(user);

        assertEquals("updatedNickname", result.getNickname());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUser_shouldThrowException_whenUserIdIsNull() {
        User user = User.of(null, "testNickname", Set.of(UserRole.USER));

        assertThrows(RuntimeException.class, () -> userWriter.updateUser(user));
    }

    @Test
    void updateUser_shouldThrowException_whenUserDoesNotExist() {
        User user = User.of(1L, "testNickname", Set.of(UserRole.USER));

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userWriter.updateUser(user));
    }

    @Test
    void deleteUserInfo_shouldMarkUserAsDeleted_whenUserExists() {
        User user = User.of(1L, "testNickname", Set.of(UserRole.USER));
        UserEntity existingEntity = UserEntity.of("encodedPassword", "testNickname", Set.of(UserRole.USER));
        FakeSetter.setField(existingEntity, "id", 1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(UserEntity.class))).thenReturn(existingEntity);

        User result = userWriter.deleteUserInfo(user);

        assertEquals("testNickname", result.getNickname());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUserInfo_shouldThrowException_whenUserIdIsNull() {
        User user = User.of(null, "testNickname", Set.of(UserRole.USER));

        assertThrows(RuntimeException.class, () -> userWriter.deleteUserInfo(user));
    }

    @Test
    void deleteUserInfo_shouldThrowException_whenUserDoesNotExist() {
        User user = User.of(1L, "testNickname", Set.of(UserRole.USER));

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userWriter.deleteUserInfo(user));
    }
}

// Note: The test class assumes that DefaultUserWriter requires both IUserEntityRepository and PasswordEncoder as dependencies.