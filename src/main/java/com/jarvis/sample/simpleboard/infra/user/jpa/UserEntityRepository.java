package com.jarvis.sample.simpleboard.infra.user.jpa;

import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.List;
import java.util.Optional;

/**
 * JPA 기반의 실제 레포지토리입니다.
 * Spring Data JPA가 자동 구현합니다.
 */
@JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_IMPL,
     references = { UserEntity.class, IUserEntityRepository.class }
)
public interface UserEntityRepository extends JpaRepository<UserEntity, Long>, IUserEntityRepository {

    @Override
    Optional<UserEntity> findById(Long id);

    @Override
    UserEntity save(UserEntity entity);

    @Override
    @Query("SELECT u FROM UserEntity u WHERE u.nickname = ?1")
    List<UserEntity> listByNickname(String nickname);

}