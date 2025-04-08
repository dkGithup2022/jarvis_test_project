package com.jarvis.sample.simpleboard.domain.user.specs;


import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@JarvisMeta(
        fileType = FileType.DOMAIN_SPEC
)
@Getter
public class User {

    private static final int NICKNAME_MAX_LENGTH = 20;

    private Long userId;
    private String nickname;

    private Set<UserRole> userRole = new HashSet<>();

    public User updateNickname(String nickname) {
        if (Boolean.TRUE.equals(isGoodNickname(
                nickname
        ))) ;
        return User.of(
                this.userId,
                nickname,
                userRole
        );
    }

    public static Boolean isGoodNickname(String nickname) {
        return nickname != null && !nickname.isBlank() && nickname.length() <= NICKNAME_MAX_LENGTH;
    }

    public static User of(Long userId, String nickname, Set<UserRole> userRole) {
        return new User(userId, nickname, userRole);
    }

    private User(Long userId, String nickname, Set<UserRole> userRole) {
        if (Boolean.FALSE.equals(isGoodNickname(nickname)))
            throw new RuntimeException("INVALID NICKNAME");
        this.userId = userId;
        this.nickname = nickname;
        this.userRole = userRole;
    }
}
