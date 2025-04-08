package com.jarvis.sample.simpleboard.infra.user.api;

import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.jpa.UserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@JarvisMeta(
    fileType = FileType.INFRA_REPOSITORY_TEST,
    references = { UserEntity.class, IUserEntityRepository.class, UserEntityRepository.class }
)
public class IUserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository repository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.of("encodedPassword", "testNickname", Set.of());
        repository.save(userEntity);
    }

    @Test
    void findById_shouldReturnUserEntityWhenIdExists() {
        Optional<UserEntity> foundEntity = repository.findById(userEntity.getId());
        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getNickname()).isEqualTo("testNickname");
    }

    @Test
    void findById_shouldReturnEmptyWhenIdDoesNotExist() {
        Optional<UserEntity> foundEntity = repository.findById(-1L);
        assertThat(foundEntity).isNotPresent();
    }

    @Test
    void save_shouldPersistUserEntity() {
        UserEntity newUserEntity = UserEntity.of("newEncodedPassword", "newNickname", Set.of());
        UserEntity savedEntity = repository.save(newUserEntity);

        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getNickname()).isEqualTo("newNickname");
    }

    @Test
    void save_shouldThrowExceptionWhenUserEntityIsNull() {
        assertThrows(Exception.class, () -> repository.save(null));
    }

    @Test
    void listByNickname_shouldReturnUserEntitiesWithMatchingNickname() {
        List<UserEntity> foundEntities = repository.listByNickname("testNickname");
        assertThat(foundEntities).hasSize(1);
        assertThat(foundEntities.get(0).getNickname()).isEqualTo("testNickname");
    }

    @Test
    void listByNickname_shouldReturnEmptyListWhenNoMatchingNickname() {
        List<UserEntity> foundEntities = repository.listByNickname("nonexistentNickname");
        assertThat(foundEntities).isEmpty();
    }
}