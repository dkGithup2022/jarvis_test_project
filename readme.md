
# 📘 목차

- [소개](#소개)
- [요약: 가설과 후기](#요약가설과-후기)
    - [가설](#가설)
    - [후기](#후기)
- [요약: 보완점](#요약--보완점)
    - [1. 레퍼런스 클래스에 대한 추론 필요](#1-레퍼런스-클래스에-대한-추론-필요)
    - [2. 제한적인 구조](#2-제한적인-구조)
- [Jarvis 생성 내역](#jarvis-생성-내역)
    - [생성된 라인 수 (Jarvis 관리 라인 수 - 6342)](#생성된-라인-수-jarvis-관리-라인-수---6342)
    - [전체 라인 수 (6817)](#전체-라인-수-6817)

## 데모영상 

![시연 GIF](https://github.com/dkGithup2022/jarvis_test_project/issues/2)
****


## 소개 

가설 검증과 사용성 테스트를 위해 jarvis cli 를 이용해서 만든 간단한 앱 어플리케이션 입니다.

파일의 역할을 정의한 뒤, 파일에 역할에 맞는 프롬프트와 shot을 정의하고 해당 프롬프트를 수행하기 위한 래퍼런스 클래스를 llm 에 담아 전송하는 방식으로 동작합니다.



7000 줄 정도의 게시판 서비스를 만드는데에 툴을 이용해서 7-8 시간 정도 걸렸는데, ux 측면 ( 후술할 래퍼런스 클래스의 감지) 를 추가함으로서 절반정도로 감축할 수 있을 것으로 보입니다.

아래는 첫 데모를 이용해 서비스를 만든 뒤, 가설에 대한 검증 / 보완점 /생성 내역에 대한 기록입니다.



### 요약:가설과 후기 

#### 가설 
- 역할별 프롬프트, shot, 올바른 레퍼런스 클래스 추가를 통해 사용가능한 코드를 생성해 낼 수 있는가 ? 

#### 후기
**YES**, 해당 프로젝트의 일반적인 게시판의 레이어별 기능, 테스트, 픽스처를 생성이 가능했다..


해당 프로젝트의 domain, infra, 의 구현체와 test, fixture 는 jarvis cli 를 통해서 자동 생성된 결과이다.
(import 경로가 깨지는 것 정도의 수정은 거친)

code quality 괜찮았고, 기능수행 측면에서 대부분의 테스트를 올바르게 만들고 통과했다. 


### 요약 : 보완점

#### 1. 레퍼런스 클래스에 대한 추론 필요

아래는 domain api 구현을 위해 컨텍스트에 레퍼런스해야 할 클래스의 목록입니다.
두가지 단점이 있는데, 

1. 유저가 직접 입력 해야함.
2. 필요한 클래스 정의가 없으면 , 생성되는 결과가 오작동함.

실제로 래퍼런스 해야 할 클래스를 바꿔가며 cli 기능을 실행해야 해서, 실제 작업에서는 클래스 당 기능수행을 2~3 번을 수행했음.

따라서, 해당 래퍼런스 클래스를 추론할 수 있는 어노테이션 구조 / 매커니즘 개발 필요 .



```java
@Component
@RequiredArgsConstructor
@JarvisMeta(
    fileType = FileType.DOMAIN_API_IMPL,
    references = { Answer.class, AnswerReader.class,
            ArticleReaderBase.class,
            ChildArticleEntity.class,
            IChildArticleEntityRepository.class,
            Popularity.class,
            PopularityEmbeddable.class,
            IUserEntityRepository.class,
            ArticleType.class
    }
)
public class DefaultAnswerReader implements AnswerReader { ... }

```

#### 2. 제한적인 구조 .

jarvis 에 내장된 file type에 맞는 template 에 대해서는 문제를 잘 해결하지만 실제로 file type 만으로는 해결 안되는 상황 많음.

특히, 진행 중인 서비스에 중간에 툴의 기능을 개입하는 것은 지금으로서는 불가능할 것으로 보임.

-> 이것은 프로젝트 스타일 별 케이스를 추가해서, general 한 영역을 커버할 수 있게 만들어야 할 것으로 보인다. (우선도 낮음)



## Jarvis 생성 내역 

데이터 스펙, 인터페이스 명세를 통하여, 구현체, 테스트, 테스트 픽스처를 자동 생성한 결과입니다.



#### 생성된 라인 수 (Jarvis 관리 라인 수 - 6342)

아래 두가지 요소입니다.

- 유저가 정의한 인터페이스 명세, 데이터 스펙 ( 조금 )
- (1) 을 통해 jarvis 로 생성된 코드 라인 수.  ( 대부분 )

```java
___

 cloc src 
     156 text files.
     156 unique files.                                          
       0 files ignored.

github.com/AlDanial/cloc v 2.04  T=0.13 s (1227.8 files/s, 64396.3 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                           155           1572            268           6341
Properties                       1              0              0              1
-------------------------------------------------------------------------------
SUM:                           156           1572            268           6342
-------------------------------------------------------------------------------
```

#### 전체 라인 수 (6817)

사용가능하게 만들기 위해서 유저가 정의한 라인까지 추가한 결과입니다. 


```shell
 cloc ./src/              
     161 text files.
     161 unique files.                                          
       0 files ignored.

github.com/AlDanial/cloc v 2.04  T=0.15 s (1107.8 files/s, 60481.8 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                           160           1689            284           6816
Properties                       1              0              0              1
-------------------------------------------------------------------------------
SUM:                           161           1689            284           6817
-------------------------------------------------------------------------------

```