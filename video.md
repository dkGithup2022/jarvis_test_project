# 비디오 만들기 


### 1. 구현체, 테스트, 픽스처 자동으로 만들어 보기 

#### 필요한 것 (직접 만들어야 하는것 )

1. 인터페이스 명세
2. 데이터 스팩 ({domain}.class, {entity}.class)

#### 만드는 것 (llm으로 만드는 것 )

1. 인터페이스의 구현체 
2. (1) 의 테스트
3. (1) 의 테스트 픽스처 


### 2. 데모 영상 찍기 

간단한 인터페이스의 구현체와 테스트 생성

1. 간단한거 
2. 그것보단 살짝 복잡한거

순서대로 할거에요. 

#### 간단한거 

```java
@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface AnnouncementReader extends ArticleReaderBase<Announcement> {
}

public interface ArticleReaderBase<R> {

    /*
     * article Id 를 통해서 문서에 접근 후 도메인 객체로 매핑하여 반환합니다.
     *
     * articleType 별로 사용해야 하는 repo 가 다릅니다.
     * - article, announcement 인 경우, IArticleEntityRepository
     * - question, discussion 인 경우, IParentArticleEntityRepository
     * - answer, discussionReply 인 경우, IChildArticleEntityRepository
     *
     * articleEntity 의 authorId 를 토대로 user 테이블에서 author nickname 을 가져옵니다.
     * */
    R read(Long articleId);
}

@JarvisMeta(
        fileType = FileType.DOMAIN_SPEC
)
@Getter
public class Announcement implements ArticleBase {

    private static final int MAX_LENGTH = 10_000;

    private Long id;
    private Long authorId;
    private String authorNickname;

    private String title;
    private String content;
    private Popularity popularity;
    private Boolean deleted;
    ...
}

```

#### 살짝 복잡한거 (말 많은거 )

```java


@JarvisMeta(
        fileType = FileType.DOMAIN_API
)
public interface UserWriter {

    /*
     * [설명]
     *    새로운 유저 데이터를  entity 로 매핑하여 save 한다.
     *     입력 받는 user 객체의 id 는 null 이여야 한다 아닐 시 runtimeException
     *
     * 유저 엔티티를 만들어 저장한다.
     * UserRole 을 Set.of(User) 로서 지정하여 저장.
     * password 는 spring 에서 사용할 수 있는 적절한 encoder 로 인코딩하여 저장한다.
     */
    User createUser(String nickname, String password);


    /*
     * [설명]
     *    새로운 유저 데이터를  entity 로 매핑하여 save 한다.
     *    1. user 정보를 조회하여 존재하는지 확인한다. 없을 경우, runtimeexception 을 반환한다.
     *    2. user.userId 에 해당하는 유저가 db 에 존재하는지 확인. 없다면 runtimeexception 을 반환한다.
     *
     *   새로운 User 값으로 매핑하여  entity 를 다시 save  한다. 같은 id 에 대해 save
     */
    User updateUser(User user);


    /*
     * [설명]
     *    새로운 유저 데이터를  entity 로 매핑하여 save 한다.
     *    1. user 정보를 조회하여 존재하는지 확인한다. 없을 경우, runtimeexception 을 반환한다.
     *    2. user.userId 에 해당하는 유저가 db 에 존재하는지 확인. 없다면 runtimeexception 을 반환한다.
     *
     *   새로 User id 에 대한 entity 를 찾아서 delete flag 를 true 로 변경한다.
     */
    User deleteUserInfo(User user);
}

```

## 하고 있는거 .

아래 래퍼런스 클래스, 입력 안받고 추론 가능하게 변경중 ... 
금방 될듯요 

```java
@Component
@RequiredArgsConstructor
@JarvisMeta(
        fileType = FileType.DOMAIN_API_IMPL,
        references = {Announcement.class, AnnouncementReader.class,
                ArticleReaderBase.class,
                UserEntity.class, IUserEntityRepository.class,
                ArticleEntity.class, IArticleEntityRepository.class,
                ArticleType.class, Popularity.class,
                PopularityMapper.class}
)
```


## 하고 싶은 말 

왕감사합니다 : ) 모두 행복하세요.