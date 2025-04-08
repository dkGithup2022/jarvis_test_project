# 📝 API 명세 (POST 요청 중심)

---

## 👤 User

### 1. 회원가입

- **POST** `/api/users`
- **Request Body**
```json
{
  "nickname": "dankim0124",
  "password": "securePass123!"
}
```

#### 📦 DTO
```java
public record UserSignUpRequest(
    String nickname,
    String password
) {}
```

---

### 2. 로그인

- **POST** `/api/users/login`
- **Request Body**
```json
{
  "nickname": "dankim0124",
  "password": "securePass123!"
}
```

#### 📦 DTO
```java
public record UserLoginRequest(
    String nickname,
    String password
) {}
```

---

## 📝 Article

### 3. 게시글 작성

- **POST** `/api/articles`
- **Request Body**
```json
{
  "type": "QUESTION",  // or "NORMAL", "ANNOUNCEMENT", "DISCUSSION"
  "title": "질문 있습니다!",
  "content": "스프링에서 이벤트 발송은 어떻게 하나요?"
}
```

#### 📦 DTO
```java
public record ArticleWriteRequest(
    ArticleType type,
    String title,
    String content
) {}
```

---

## 💬 Comment

### 4. 댓글 작성 (댓글/대댓글 공통)

- **POST** `/api/comments`
- **Request Body**
```json
{
  "articleType": "QUESTION",
  "articleId": 1234,
  "parentCommentId": null,
  "content": "좋은 질문이네요!"
}
```

#### 📦 DTO
```java
public record CommentWriteRequest(
    ArticleType articleType,
    Long articleId,
    Long parentCommentId, // nullable, 대댓글이면 값 있음
    String content
) {}
```

---

> 📌 `ArticleType`은 Enum으로 관리 (예: `QUESTION`, `NORMAL`, `ANNOUNCEMENT`, `DISCUSSION`)
