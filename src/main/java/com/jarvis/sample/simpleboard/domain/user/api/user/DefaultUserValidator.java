package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { User.class, UserValidator.class }
)
@RequiredArgsConstructor
@Component
public class DefaultUserValidator implements UserValidator {

    private final IUserEntityRepository userEntityRepository;

    @Override
    public Boolean canUseNickname(String nickname) {
        if (!User.isGoodNickname(nickname)) {
            return false;
        }
        return userEntityRepository.listByNickname(nickname).isEmpty();
    }

    @Override
    public Boolean canUpdate(Long requestUserId, User user) {
        if (!requestUserId.equals(user.getUserId())) {
            return false;
        }
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(user.getUserId());
        if (userEntityOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return true;
    }

    @Override
    public Boolean canDelete(Long requestUserId, User user) {
        if (!requestUserId.equals(user.getUserId())) {
            return false;
        }
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(user.getUserId());
        if (userEntityOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return true;
    }
}

/*
* Assumptions:
* 1. `IUserEntityRepository` is assumed to be a repository interface with methods for interacting with user data.
* 2. `User` class has methods to validate nickname and get userId.
* 3. Exception handling is simplistic for demonstration purposes and might be improved with specific exception types.
*/