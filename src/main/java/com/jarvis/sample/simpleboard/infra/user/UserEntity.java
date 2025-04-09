package com.jarvis.sample.simpleboard.infra.user;


import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.jarvisAnnotation.FileType;
import com.jarvis.sample.simpleboard.jarvisAnnotation.JarvisMeta;

import com.jarvis.sample.simpleboard.infra.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@JarvisMeta(
        fileType = FileType.INFRA_ENTITY
)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passwordEncoded;

    private String nickname;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<UserRole> userRole = new HashSet<>();

    public UserEntity updateNickname(String nickname) {
        return new UserEntity(
                this.id,
                this.passwordEncoded,
                nickname,
                this.userRole
        );
    }

    public static UserEntity of(String passwordEncoded, String nickname, Set<UserRole> userRole) {
        return new UserEntity(null, passwordEncoded, nickname, userRole);
    }


    private UserEntity(Long id, String passwordEncoded, String nickname, Set<UserRole> userRole) {
        this.id = id;
        this.passwordEncoded = passwordEncoded;
        this.nickname = nickname;
        this.userRole = userRole;
    }
}