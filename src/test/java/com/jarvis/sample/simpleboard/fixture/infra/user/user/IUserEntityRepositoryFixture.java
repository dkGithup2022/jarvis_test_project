package com.jarvis.sample.simpleboard.fixture.infra.user.user;

import com.jarvis.sample.simpleboard.FakeSetter;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;

import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IUserEntityRepository 테스트용 픽스처 클래스.
 * 필요한 경우 테스트용 데이터를 생성하는 메서드를 추가하세요.
 */

 @JarvisMeta(
     fileType = FileType.INFRA_REPOSITORY_FIXTURE,
     references = { UserEntity.class, IUserEntityRepository.class }
 )

public class IUserEntityRepositoryFixture implements IUserEntityRepository{
 private final Map<Long, UserEntity> db = new HashMap<>();
 private final AtomicLong idGenerator = new AtomicLong(1);

 @Override
 public Optional<UserEntity> findById(Long id) {
  return Optional.ofNullable(db.get(id));
 }

 @Override
 public UserEntity save(UserEntity entity) {
  if (entity.getId() != null && db.containsKey(entity.getId())) {
   db.put(entity.getId(), entity);
   return entity;
  }

  long newId = idGenerator.getAndIncrement();
  FakeSetter.setField(entity, "id", newId);
  db.put(newId, entity);
  return entity;
 }

 @Override
 public List<UserEntity> listByNickname(String nickname) {
  return db.values().stream()
          .filter(e -> e.getNickname().equals(nickname))
          .toList();
 }

 // for test only
 public void clear() {
  db.clear();
  idGenerator.set(1);
 }

}
