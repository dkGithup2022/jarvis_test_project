
package com.jarvis.sample.simpleboard.fixture.user.user;

import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

/**
 * IUserEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { UserEntity.class, IUserEntityRepository.class }
 )

public class IUserEntityRepositoryFixture {
    // TODO: implement fixture methods if needed
}
