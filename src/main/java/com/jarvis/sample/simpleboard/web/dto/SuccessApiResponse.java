package com.jarvis.sample.simpleboard.web.dto;

public record SuccessApiResponse<T>(
        String status,     // SUCCESS or FAILURE
        T data            // 성공 시 payload, 실패 시 null

) {
    public static <T> SuccessApiResponse<T> success(T data) {
        return new SuccessApiResponse<>("SUCCESS", data);
    }

}
