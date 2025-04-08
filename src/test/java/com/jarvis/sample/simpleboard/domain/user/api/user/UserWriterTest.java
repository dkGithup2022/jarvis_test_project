package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.domain.user.specs.User;
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
    references = { User.class, DefaultUserWriter.class, UserWriter.class }
)
public class UserWriterTest {

    private IUserEntityRepository repository;
    private DefaultUserWriter userWriter;

    @BeforeEach
    void setup() {
        repository = mock(IUserEntityRepository.class);
        userWriter = new DefaultUserWriter(repository);
    }

    @Test
    void createUser_shouldSaveNewUser_whenUserIdIsNull() {
        User user = User.of(null, "testNickname", Set.of(UserRole.USER));
        UserEntity userEntity = UserEntity.of(null, "testNickname", Set.of(UserRole.USER));
        UserEntity savedEntity = new UserEntity(1L, null, "testNickname", Set.of(UserRole.USER));

        when(repository.save(any(UserEntity.class))).thenReturn(savedEntity);

        User result = userWriter.createUser(user);

        assertNotNull(result.getUserId());
        assertEquals("testNickname", result.getNickname());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_shouldThrowException_whenUserIdIsNotNull() {
        User user = User.of(1L, "testNickname", Set.of(UserRole.USER));

        assertThrows(RuntimeException.class, () -> userWriter.createUser(user));
    }

    @Test
    void updateUser_shouldUpdateUser_whenUserExists() {
        User user = User.of(1L, "updatedNickname", Set.of(UserRole.USER));
        UserEntity existingEntity = new UserEntity(1L, null, "currentNickname", Set.of(UserRole.USER));
        UserEntity updatedEntity = new UserEntity(1L, null, "updatedNickname", Set.of(UserRole.USER));

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
        UserEntity existingEntity = new UserEntity(1L, null, "testNickname", Set.of(UserRole.USER));

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