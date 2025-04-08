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
    references = { User.class, UserReader.class }
)
public class DefaultUserReader implements UserReader {

    private final IUserEntityRepository userEntityRepository;

    @Override
    public User findById(Long userId) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return User.of(userEntity.getId(), userEntity.getNickname(), userEntity.getUserRole());
        }
        return null;
    }
}