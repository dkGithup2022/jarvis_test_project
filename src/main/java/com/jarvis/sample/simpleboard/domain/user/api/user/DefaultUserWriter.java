package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {User.class, UserWriter.class}
)
public class DefaultUserWriter implements UserWriter {

    private final IUserEntityRepository userEntityRepository;

    @Override
    public User createUser(User user) {
        if (user.getUserId() != null) {
            throw new RuntimeException("User ID must be null for new users");
        }
        UserEntity userEntity = UserEntity.of(null, user.getNickname(), user.getUserRole());
        UserEntity savedEntity = userEntityRepository.save(userEntity);
        return User.of(savedEntity.getId(), savedEntity.getNickname(), savedEntity.getUserRole());
    }

    @Override
    public User updateUser(User user) {
        if (user.getUserId() == null) {
            throw new RuntimeException("User ID must not be null for updating users");
        }
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(user.getUserId());
        if (!optionalUserEntity.isPresent()) {
            throw new RuntimeException("User with ID " + user.getUserId() + " does not exist");
        }
        UserEntity userEntity = optionalUserEntity.get().updateNickname(user.getNickname());
        UserEntity updatedEntity = userEntityRepository.save(userEntity);
        return User.of(updatedEntity.getId(), updatedEntity.getNickname(), updatedEntity.getUserRole());
    }

    @Override
    public User deleteUserInfo(User user) {
        if (user.getUserId() == null) {
            throw new RuntimeException("User ID must not be null for deleting users");
        }
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(user.getUserId());
        if (!optionalUserEntity.isPresent()) {
            throw new RuntimeException("User with ID " + user.getUserId() + " does not exist");
        }
        UserEntity userEntity = optionalUserEntity.get();
        // Assuming there is a delete flag or method to handle deletion logic
        // userEntity.setDeleted(true);
        UserEntity updatedEntity = userEntityRepository.save(userEntity);
        return User.of(updatedEntity.getId(), updatedEntity.getNickname(), updatedEntity.getUserRole());
    }
}