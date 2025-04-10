package com.jarvis.sample.simpleboard.web.user;

import com.jarvis.sample.simpleboard.common.type.UserRole;
import com.jarvis.sample.simpleboard.domain.user.api.user.PasswordEncoder;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserReader;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserValidator;
import com.jarvis.sample.simpleboard.domain.user.api.user.UserWriter;
import com.jarvis.sample.simpleboard.domain.user.specs.User;
import com.jarvis.sample.simpleboard.infra.user.UserEntity;
import com.jarvis.sample.simpleboard.infra.user.api.IUserEntityRepository;
import com.jarvis.sample.simpleboard.web.dto.SuccessApiResponse;
import com.jarvis.sample.simpleboard.web.user.dto.CanUseNicknameRequest;
import com.jarvis.sample.simpleboard.web.user.dto.UserLoginRequest;
import com.jarvis.sample.simpleboard.web.user.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserReader userReader;
    private final UserWriter userWriter;
    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;
    private final IUserEntityRepository userEntityRepository;


    @PostMapping("signUp")
    public ResponseEntity<SuccessApiResponse<User>> signUp(UserSignUpRequest request) {
        var pwEncoded = passwordEncoder.encode(request.password());
        var nickname = request.nickname();

        if (!userValidator.canUseNickname(nickname))
            throw new RuntimeException("겹치는 안이디");

        userEntityRepository.save(UserEntity.of(pwEncoded, nickname, Set.of(UserRole.USER)));
        var created = userReader.findByNickname(nickname);
        return ResponseEntity.ok(SuccessApiResponse.success(created));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessApiResponse<String>> login(UserLoginRequest request) {
        var accounts = userEntityRepository.listByNickname(request.nickname());

        if (accounts.size() != 1)
            throw new RuntimeException("nickname 으로 로그인 불가");

        if (!accounts.getFirst().getPasswordEncoded().equals(passwordEncoder.encode(request.password())))
            throw new RuntimeException("비밀번호 불일치");


        return ResponseEntity.ok(SuccessApiResponse.success("success"));
    }


    @PostMapping("/canUseNickname")
    public ResponseEntity<SuccessApiResponse<Boolean>> canUseNickname(CanUseNicknameRequest request) {
        var available = userValidator.canUseNickname(request.nickname());
        return ResponseEntity.ok(SuccessApiResponse.success(available));
    }

}
