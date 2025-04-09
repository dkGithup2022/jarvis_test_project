package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {User.class, UserWriter.class,
                IUserEntityRepository.class, User.class, UserEntity.class, UserRole.class
        }
)
public class DefaultUserWriter implements UserWriter {

    private final IUserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String nickname, String password) {
        if (nickname == null || password == null) {
            throw new RuntimeException("Nickname and password must not be null");
        }
        UserEntity userEntity = UserEntity.of(passwordEncoder.encode(password), nickname, Set.of(UserRole.USER));
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

// Note: The DefaultUserWriter class assumes the existence of a PasswordEncoder bean for encoding passwords. 
// The deleteUserInfo method currently lacks a concrete implementation for marking a user as deleted; 
// this would need to be implemented in the UserEntity class as indicated by the inline comment.