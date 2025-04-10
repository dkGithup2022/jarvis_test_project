package com.jarvis.sample.simpleboard.domain.user.api.user;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class PasswordEncoder {

    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
