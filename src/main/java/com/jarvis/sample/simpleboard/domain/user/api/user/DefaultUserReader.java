package com.jarvis.sample.simpleboard.domain.user.api.user;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import com.jarvis.sample.simpleboard.domain.user.specs.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {User.class, UserReader.class,
                IUserEntityRepository.class,
                UserEntity.class,
                UserRole.class
        }
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

    @Override
    public User findByNickname(String nickname) {
        List<UserEntity> userEntities = userEntityRepository.listByNickname(nickname);
        
        if (userEntities.size() > 1) {
            throw new RuntimeException("Multiple users found with the same nickname");
        }
        
        if (!userEntities.isEmpty()) {
            UserEntity userEntity = userEntities.get(0);
            return User.of(userEntity.getId(), userEntity.getNickname(), userEntity.getUserRole());
        }
        
        return null;
    }
}