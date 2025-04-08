package com.jarvis.sample.simpleboard.web.user;

import com.jarvis.sample.simpleboard.web.dto.SuccessApiResponse;
import com.jarvis.sample.simpleboard.web.user.dto.CanUseNicknameRequest;
import com.jarvis.sample.simpleboard.web.user.dto.UserLoginRequest;
import com.jarvis.sample.simpleboard.web.user.dto.UserSignUpRequest;
import org.springframework.http.ResponseEntity;

public class UserController {


    public ResponseEntity<SuccessApiResponse> signUp(UserSignUpRequest request) {
        return null;
    }

    public ResponseEntity<SuccessApiResponse> login(UserLoginRequest request) {
        return null;
    }

    public ResponseEntity<SuccessApiResponse> canUseNickname(CanUseNicknameRequest request) {
        return null;
    }

}
